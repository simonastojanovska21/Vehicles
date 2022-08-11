package com.vehicle.core.services.impl;

import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.CarDto;
import com.vehicle.core.models.dto.CarItem;
import com.vehicle.core.models.exceptions.InvalidDataException;
import com.vehicle.core.models.exceptions.NodeAlreadyExistException;
import com.vehicle.core.services.QueryService;
import com.vehicle.core.utils.Constants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CarServiceImplTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private CarServiceImpl carService;

    private final ResourceResolverFactory resourceResolverFactory = mock(ResourceResolverFactory.class);
    private final ResourceResolver resourceResolver = mock(ResourceResolver.class);
    private final QueryService queryService = mock(QueryServiceImpl.class);
    private final SlingHttpServletRequest request = mock(SlingHttpServletRequest.class);
    private Session session;


    @BeforeEach
    void setUp() throws LoginException {
        context.load().json("/com/vehicle/core/services/CarData.json","/content/vehicle/carData");
        session = context.resourceResolver().adaptTo(Session.class);
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, ConstantsForTesting.VEHICLE_SERVICE_USER);
        when(resourceResolverFactory.getServiceResourceResolver(paramMap)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        context.registerService(queryService);
        context.registerInjectActivateService(carService);
    }

    @Test
    void getAllCars() throws LoginException {
        Resource resource = context.currentResource(ConstantsForTesting.CARS_NODE_LOCATION);
        assertNotNull(resource);
        when(queryService.getAllCarsQuery(any(Session.class))).thenReturn(resource.getChildren().iterator());
        List<Car> cars = carService.getAllCars();
        assertEquals(27,cars.size());
        cars.forEach(Assertions::assertNotNull);
    }

    @Test
    void filterCars() throws LoginException {
        Resource resource = context.currentResource(ConstantsForTesting.FILTERED_CAR_ITEMS_LOCATION);
        assertNotNull(resource);
        when(queryService.getFilteredCars(any(Session.class),any(String.class),any(String.class),any(String.class)))
                .thenReturn(resource.getChildren().iterator());
        List<CarItem> carItems = carService.filterCars("449","All","All");
        assertEquals(6,carItems.size());
        carItems.forEach(Assertions::assertNotNull);
    }

    @Test
    void filterCarsWithEmptyFields(){
        Resource resource = context.currentResource(ConstantsForTesting.CARS_NODE_EMPTY_FIELD_LOCATION);
        assertNotNull(resource);
        when(queryService.getFilteredCars(any(Session.class),any(String.class),any(String.class),any(String.class)))
                .thenReturn(resource.getChildren().iterator());
        assertThrows(InvalidDataException.class,()->carService.filterCars("449","All","All"),
                "Blank fields detected when creating car item");
    }

    @Test
    void getDetailsAboutCar() throws LoginException {
        Resource resource = context.currentResource(ConstantsForTesting.CARS_DETAILS_NODE_LOCATION);
        assertNotNull(resource);
        when(queryService.getCarDetailsQuery(any(Session.class),any(String.class))).thenReturn(resource);
        Car expected = new Car("c65b272e-c359-4ed6-ae3a-de3f2b5e08ee",441,"Tesla",
        10199,"Model x","/apps/vehicle/components/header/clientlib/resources/carLogo.png",
                2022,7000,"Automatic","Sedan");
        Car actual = carService.getDetailsAboutCar("c65b272e-c359-4ed6-ae3a-de3f2b5e08ee");
        assertEquals(expected,actual);
    }

//    @Test
//    void createCarResourceWithBlankField(){
//        Resource resource = context.currentResource("/content/vehicle/carData/carResourceWithBlankField/car1");
//        assertNotNull(resource);
//        when(queryService.getCarDetailsQuery(any(Session.class),any(String.class))).thenReturn(resource);
//        assertThrows(InvalidDataException.class,()->carService.getDetailsAboutCar("ebd3dcca-ad93-445d-8f33-a3da01f156ea"),
//                "Blank fields detected when creating car item");
//
//    }
    @Test
    void addNewCarToRepository() throws RepositoryException {
        Node cars = session.getNode(ConstantsForTesting.CARS_NODE_LOCATION);
        assertNotNull(cars);
        int sizeBeforeAdd = (int) cars.getNodes().getSize();
        CarDto carDto = new CarDto(452,"Bmw",1719,"X3","/apps/vehicle/components/header/clientlib/resources/carLogo.png",
                2022,3000,"Automatic","SUV");
        carService.addNewCarToRepository(carDto,session);
        int sizeAfterAdd = (int) cars.getNodes().getSize();
        assertEquals(sizeBeforeAdd+1,sizeAfterAdd);
    }

    @Test
    void processAddNewCarPostRequest() throws IOException, LoginException, RepositoryException {
        Node cars = session.getNode(ConstantsForTesting.CARS_NODE_LOCATION);
        assertNotNull(cars);
        assertFalse(session.hasPendingChanges());

        int sizeBeforeAdd = (int) cars.getNodes().getSize();
        String carJSON = "{\"brandId\":\"454\",\n" +
                "\"brandName\":\"Bugatti\",\n" +
                "\"carModelId\":\"1734\",\n" +
                "\"carModelName\":\"Veyron\",\n" +
                "\"imageUrl\":\"https://www.fitfatherproject.com/wp-content/uploads/2017/09/meal-prep-400.jpg\",\n" +
                "\"year\":\"1\",\n" +
                "\"kilometers\":\"1\",\n" +
                "\"transmission\":\"Automatic\",\n" +
                "\"bodyStyle\":\"SUV\"\n" +
                "}\n";
        BufferedReader reader = new BufferedReader(new StringReader(carJSON));
        when(request.getReader()).thenReturn(reader);
        carService.processAddNewCarPostRequest(request);
        assertFalse(session.hasPendingChanges());
        session.logout();
        int sizeAfterAdd = (int) cars.getNodes().getSize();
        assertEquals(sizeBeforeAdd+1,sizeAfterAdd);
    }


}