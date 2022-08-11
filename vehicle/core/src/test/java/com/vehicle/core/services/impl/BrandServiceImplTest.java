package com.vehicle.core.services.impl;

import com.vehicle.core.models.Brand;
import com.vehicle.core.models.exceptions.InvalidDataException;
import com.vehicle.core.services.QueryService;
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
class BrandServiceImplTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private BrandServiceImpl brandService;

    private final ResourceResolverFactory resourceResolverFactory = mock(ResourceResolverFactory.class);
    private final ResourceResolver resourceResolver = mock(ResourceResolver.class);
    private final QueryService queryService = mock(QueryServiceImpl.class);
    private Session session;
    @BeforeEach
    void setUp() throws LoginException {
        context.load().json("/com/vehicle/core/services/BrandData.json","/content/vehicle/carData");
        session = context.resourceResolver().adaptTo(Session.class);
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, ConstantsForTesting.VEHICLE_SERVICE_USER);
        when(resourceResolverFactory.getServiceResourceResolver(paramMap)).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);

        context.registerService(queryService);
        context.registerInjectActivateService(brandService);
    }

    @Test
    void getAllBrands() throws LoginException {
        Resource resource = context.currentResource(ConstantsForTesting.BRANDS_NODE_LOCATION);
        assertNotNull(resource);
        when(queryService.getAllBrandsQuery(any(Session.class))).thenReturn(resource.getChildren().iterator());
        List<Brand> brands = brandService.getAllBrands();
        assertEquals(5,brands.size());
        brands.forEach(Assertions::assertNotNull);
    }

    @Test
    void addNewBrandToRepository() throws RepositoryException {
        Node brands = session.getNode(ConstantsForTesting.BRANDS_NODE_LOCATION);
        assertNotNull(brands);
        int sizeBeforeAdd = (int) brands.getNodes().getSize();
        brandService.addNewBrandToRepository(456,"Test brand name", session);
        int sizeAfterAdd = (int) brands.getNodes().getSize();
        assertEquals(sizeBeforeAdd+1,sizeAfterAdd);
        Node addedNode = session.getNode(ConstantsForTesting.BRANDS_NODE_LOCATION+"/Brand456");
        assertNotNull(addedNode);
        assertEquals("456",addedNode.getProperty("BrandId").getString());
        assertEquals("Test brand name",addedNode.getProperty("BrandName").getString());
    }

    @Test
    void createBrandResourceWithBlankField(){
        Resource resource = context.currentResource(ConstantsForTesting.BRANDS_NODE_EMPTY_FIELD_LOCATION);
        assertNotNull(resource);
        when(queryService.getAllBrandsQuery(any(Session.class))).thenReturn(resource.getChildren().iterator());
        assertThrows(InvalidDataException.class,
                ()->brandService.getAllBrands(),
                "Blank fields detected when creating brand object");
    }
}