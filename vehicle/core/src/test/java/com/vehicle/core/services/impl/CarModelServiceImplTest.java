package com.vehicle.core.services.impl;

import com.vehicle.core.models.CarModel;
import com.vehicle.core.models.exceptions.InvalidDataException;
import com.vehicle.core.services.QueryService;
import com.vehicle.core.utils.Constants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CarModelServiceImplTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private CarModelServiceImpl carModelService;

    private final ResourceResolverFactory resourceResolverFactory = mock(ResourceResolverFactory.class);
    private final ResourceResolver resourceResolver = mock(ResourceResolver.class);
    private final QueryService queryService = mock(QueryServiceImpl.class);
    private Session session;

    @BeforeEach
    void setUp() throws LoginException {
        context.load().json("/com/vehicle/core/services/CarModelData.json","/content/vehicle/carData");
        session = context.resourceResolver().adaptTo(Session.class);
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, ConstantsForTesting.VEHICLE_SERVICE_USER);
        when(resourceResolverFactory.getServiceResourceResolver(paramMap)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        context.registerService(queryService);
        context.registerInjectActivateService(carModelService);
    }

    @Test
    void getAllCarModels() throws LoginException {
        Resource resource = context.currentResource(ConstantsForTesting.CAR_MODELS_NODE_LOCATION);
        assertNotNull(resource);
        when(queryService.getAllCarModelsQuery(any(Session.class))).thenReturn(resource.getChildren().iterator());
        List<CarModel> carModels = carModelService.getAllCarModels();
        assertEquals(24,carModels.size());
        carModels.forEach(Assertions::assertNotNull);
    }

    @Test
    void getCarModelsForBrand() throws LoginException {
        Resource resource = context.currentResource(ConstantsForTesting.CAR_MODELS_FOR_BRAND_NODE_LOCATION);
        assertNotNull(resource);
        when(queryService.getAllCarModelsForBrandQuery(any(Session.class),any(String.class)))
                .thenReturn(resource.getChildren().iterator());
        List<CarModel> carModels = carModelService.getCarModelsForBrand("452");
        assertEquals(13,carModels.size());
        carModels.forEach(Assertions::assertNotNull);
    }

    @Test
    void addNewCarModelToRepository() throws RepositoryException {
        Node carModels = session.getNode(ConstantsForTesting.CAR_MODELS_NODE_LOCATION);
        assertNotNull(carModels);
        int sizeBeforeAdd = (int) carModels.getNodes().getSize();
        carModelService.addNewCarModelToRepository(1234,"Test car model",452,session);
        int sizeAfterAdd = (int) carModels.getNodes().getSize();
        assertEquals(sizeBeforeAdd+1, sizeAfterAdd);
        Node addedNode = session.getNode(ConstantsForTesting.CAR_MODELS_NODE_LOCATION+"/CarModel1234");
        assertNotNull(addedNode);
        assertEquals("1234",addedNode.getProperty("CarModelId").getString());
        assertEquals("Test car model",addedNode.getProperty("CarModelName").getString());
        assertEquals("452",addedNode.getProperty("BrandId").getString());
    }

    @Test
    void createCarModelResourceWithBlankField(){
        Resource resource = context.currentResource(ConstantsForTesting.CAR_MODELS_NODE_EMPTY_FIELD_LOCATION);
        assertNotNull(resource);
        when(queryService.getAllCarModelsQuery(any(Session.class))).thenReturn(resource.getChildren().iterator());
        assertThrows(InvalidDataException.class,
                ()->carModelService.getAllCarModels(),
                "Blank fields detected when creating car model object");
    }
}