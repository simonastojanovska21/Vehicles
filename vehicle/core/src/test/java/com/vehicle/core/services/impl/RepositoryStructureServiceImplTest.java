package com.vehicle.core.services.impl;

import com.vehicle.core.ConstantsForTesting;
import com.vehicle.core.models.dto.CarDto;
import com.vehicle.core.models.exceptions.NodeAlreadyExistException;
import com.vehicle.core.services.*;
import com.vehicle.core.utils.Constants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class RepositoryStructureServiceImplTest {


    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    private RepositoryStructureServiceImpl repositoryStructureService;

    private final BrandService brandService = mock(BrandServiceImpl.class);
    private final CarModelService carModelService = mock(CarModelServiceImpl.class);
    private final CarService carService = mock(CarServiceImpl.class);
    private final HttpClientService httpClientService = mock(HttpClientServiceImpl.class);


    private final ResourceResolver resourceResolver = mock(ResourceResolver.class);
    private final ResourceResolverFactory resourceResolverFactory = mock(ResourceResolverFactory.class);
    private  Session session;
    private Node vehicleNode;
    @BeforeEach
    void setUp() throws LoginException, RepositoryException {
        session = context.resourceResolver().adaptTo(Session.class);
        assert session != null;
        Node contentNode = session.getRootNode().addNode("content");
        vehicleNode = contentNode.addNode("vehicle");
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, ConstantsForTesting.VEHICLE_SERVICE_USER);
        given(resourceResolverFactory.getServiceResourceResolver(paramMap)).willReturn(resourceResolver);
        given(resourceResolver.adaptTo(Session.class)).willReturn(session);

        context.registerService(brandService);
        context.registerService(carModelService);
        context.registerService(carService);
        context.registerService(httpClientService);
        repositoryStructureService = context.registerInjectActivateService(new RepositoryStructureServiceImpl());
    }

    @Test
    void createRepositoryStructure() throws RepositoryException, LoginException {
        repositoryStructureService.createRepositoryStructure();
        assertNotNull(session);
        assertFalse(session.hasPendingChanges());

        Node root = session.getNode(ConstantsForTesting.ROOT_NODE_LOCATION);
        assertNotNull(root);
        Node carData = session.getNode(ConstantsForTesting.CAR_DATA_NODE_LOCATION);
        assertNotNull(carData);
        Node brandsNode =  session.getNode(ConstantsForTesting.BRANDS_NODE_LOCATION);
        assertNotNull(brandsNode);
        Node carModelsNode = session.getNode(ConstantsForTesting.CAR_MODELS_NODE_LOCATION);
        assertNotNull(carModelsNode);
        Node cars = session.getNode(ConstantsForTesting.CARS_NODE_LOCATION);
        assertNotNull(cars);

    }

    @Test()
    void createRepositoryStructureWithExistingCarDataNode() throws RepositoryException {
        vehicleNode.addNode("carData");
        Node carData = session.getNode(ConstantsForTesting.CAR_DATA_NODE_LOCATION);
        assertNotNull(carData);
        assertThrows(NodeAlreadyExistException.class,()->repositoryStructureService.createRepositoryStructure(),
                "Car data node already exists");
    }

    @Test
    void importBrandsAndCarMakes() throws LoginException, RepositoryException, IOException, InterruptedException {
        String jsonResult = "[{\"Make_ID\":452,\"Make_Name\":\"Bmw\",\"Model_ID\":5200,\"Model_Name\":\"850csi\"}," +
                            "{\"Make_ID\":452,\"Make_Name\":\"Bmw\",\"Model_ID\":5238,\"Model_Name\":\"328is\"}," +
                            "{\"Make_ID\":476,\"Make_Name\":\"Dodge\",\"Model_ID\":3680,\"Model_Name\":\"Magnum\"}," +
                            "{\"Make_ID\":476,\"Make_Name\":\"Dodge\",\"Model_ID\":1940,\"Model_Name\":\"Nitro\"}," +
                            "{\"Make_ID\":582,\"Make_Name\":\"Audi\",\"Model_ID\":3678,\"Model_Name\":\"S8\"}]";
        given(httpClientService.getCarModelsForBrandInJSON(any(String.class))).willReturn(jsonResult);
        Node carData = vehicleNode.addNode("carData");
        Node brandsNodeRootNode = carData.addNode("brands");
        Node carModelsRootNode = carData.addNode("carModels");
        willAnswer(invocationOnMock -> {
            int make_id = invocationOnMock.getArgument(0);
            String make_name = invocationOnMock.getArgument(1);
            Session session = invocationOnMock.getArgument(2);
            assertNotNull(session);
            Node brandNode = brandsNodeRootNode.addNode("Brand"+make_id);
            brandNode.setProperty("BrandId",make_id);
            brandNode.setProperty("BrandName", make_name);
            return null;
        }).given(brandService).addNewBrandToRepository(any(int.class),any(String.class),any(Session.class));
        willAnswer(invocationOnMock -> {
            int model_id = invocationOnMock.getArgument(0);
            String model_name = invocationOnMock.getArgument(1);
            int make_id = invocationOnMock.getArgument(2);
            Session session = invocationOnMock.getArgument(3);
            assertNotNull(session);
            Node carModel = carModelsRootNode.addNode("CarModel"+model_id);
            carModel.setProperty("CarModelId",model_id);
            carModel.setProperty("CarModelName",model_name);
            carModel.setProperty("BrandId",make_id);
            return null;
        }).given(carModelService).addNewCarModelToRepository(any(int.class),any(String.class),any(int.class),any(Session.class));
        repositoryStructureService.importBrandsAndCarMakes();
        assertNotNull(session);
        assertFalse(session.hasPendingChanges());
        assertEquals(3,session.getNode(Constants.BRANDS_NODE_LOCATION).getNodes().getSize());
        assertEquals(5,session.getNode(Constants.CAR_MODELS_NODE_LOCATION).getNodes().getSize());
    }

    @Test
    void importCars() throws LoginException, RepositoryException, IOException, InterruptedException {
        Node carData = vehicleNode.addNode("carData");
        Node carRootNode = carData.addNode("cars");
        willAnswer(invocationOnMock -> {
            CarDto car = invocationOnMock.getArgument(0);
            assertNotNull(car);
            Session session = invocationOnMock.getArgument(1);
            assertNotNull(session);
            String carId = UUID.randomUUID().toString();
            Node carNode = carRootNode.addNode("Car-"+ carId);
            carNode.setProperty("CarId",carId);
            carNode.setProperty("BrandId",car.getBrandId());
            carNode.setProperty("BrandName",car.getBrandName());
            carNode.setProperty("CarModelId", car.getCarModelId());
            carNode.setProperty("CarModelName",car.getCarModelName());
            carNode.setProperty("ImageUrl",car.getImageUrl());
            carNode.setProperty("Year",car.getYear());
            carNode.setProperty("Kilometers",car.getKilometers());
            carNode.setProperty("Transmission",car.getTransmission());
            carNode.setProperty("BodyStyle",car.getBodyStyle());
            return null;
        }).given(carService).addNewCarToRepository(any(CarDto.class),any(Session.class));
        repositoryStructureService.importCars();
        assertNotNull(session);
        assertFalse(session.hasPendingChanges());
        assertEquals(26,session.getNode(Constants.CARS_NODE_LOCATION).getNodes().getSize());
    }

}