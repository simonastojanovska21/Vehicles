package com.vehicle.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.vehicle.core.models.exceptions.CarNotFoundException;
import com.vehicle.core.utils.Constants;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class QueryServiceImplTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private QueryServiceImpl queryService;

    private final QueryBuilder queryBuilder = mock(QueryBuilder.class);

    private final Query query = mock(Query.class);

    private final SearchResult searchResult = mock(SearchResult.class);

    private List<Resource> resourceList;

    private Session session;

    @BeforeEach
    void setUp(){
        context.load().json("/com/vehicle/core/services/QueryData.json", Constants.CAR_DATA_NODE_LOCATION);
        session = context.resourceResolver().adaptTo(Session.class);
        context.registerAdapter(ResourceResolver.class, QueryBuilder.class, queryBuilder);
    }

    @Test
    void getAllBrandsQuery() throws RepositoryException, IOException {
        Resource resource = context.currentResource(Constants.BRANDS_NODE_LOCATION);
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.BRANDS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());

        List<Resource> list =  new ArrayList<>();
        queryService.getAllBrandsQuery(session).forEachRemaining(list::add);

        assertEquals(3,list.size());

        assertEquals("Audi",list.get(0).getValueMap().get("BrandName"));
        assertEquals("Mercedes-benz",list.get(1).getValueMap().get("BrandName"));
        assertEquals("Bmw",list.get(2).getValueMap().get("BrandName"));

        assertEquals("582",list.get(0).getValueMap().get("BrandId",String.class));
        assertEquals("449",list.get(1).getValueMap().get("BrandId",String.class));
        assertEquals("452",list.get(2).getValueMap().get("BrandId",String.class));
    }

    @Test
    void getAllCarModelsQuery() {
        Resource resource = context.currentResource(Constants.CAR_MODELS_NODE_LOCATION);
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CAR_MODELS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());
        List<Resource> list =  new ArrayList<>();
        this.queryService.getAllCarModelsQuery(session).forEachRemaining(list::add);

        assertEquals(3,list.size());

        assertEquals("3680",list.get(0).getValueMap().get("CarModelId",String.class));
        assertEquals("1940",list.get(1).getValueMap().get("CarModelId",String.class));
        assertEquals("26625",list.get(2).getValueMap().get("CarModelId",String.class));

        assertEquals("Magnum",list.get(0).getValueMap().get("CarModelName"));
        assertEquals("Nitro",list.get(1).getValueMap().get("CarModelName"));
        assertEquals("R 1250 gs",list.get(2).getValueMap().get("CarModelName"));

        assertEquals("476",list.get(0).getValueMap().get("BrandId",String.class));
        assertEquals("476",list.get(1).getValueMap().get("BrandId",String.class));
        assertEquals("452",list.get(2).getValueMap().get("BrandId",String.class));
    }

    @Test
    void getAllCarModelsForBrandQuery() {
        Resource resource = context.currentResource("/content/vehicle/carData/carModelsForBrand");
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CAR_MODELS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("BrandId", pg.get(2).getParameters().get("property"));
            assertEquals("476", pg.get(2).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());
        List<Resource> list =  new ArrayList<>();
        this.queryService.getAllCarModelsForBrandQuery(session,"476").forEachRemaining(list::add);

        assertEquals(2,list.size());

        assertEquals("3680",list.get(0).getValueMap().get("CarModelId",String.class));
        assertEquals("1940",list.get(1).getValueMap().get("CarModelId",String.class));

        assertEquals("Magnum",list.get(0).getValueMap().get("CarModelName"));
        assertEquals("Nitro",list.get(1).getValueMap().get("CarModelName"));

        assertEquals("476",list.get(0).getValueMap().get("BrandId",String.class));
        assertEquals("476",list.get(1).getValueMap().get("BrandId",String.class));
    }

    @Test
    void getAllCarsQuery() {
        Resource resource = context.currentResource(Constants.CARS_NODE_LOCATION);
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CARS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());
        List<Resource> list =  new ArrayList<>();
        this.queryService.getAllCarsQuery(session).forEachRemaining(list::add);

        assertEquals(27,list.size());

    }

    @Test
    void getCarDetailsQuery() {
        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CARS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("CarId", pg.get(2).getParameters().get("property"));
            assertEquals("a2007ce8-cf09-465b-93f7-36fef971325f", pg.get(2).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        resourceList = new ArrayList<>();
        resourceList.add(context.currentResource(Constants.CARS_NODE_LOCATION + "/car4"));
        given(searchResult.getResources()).willReturn(resourceList.iterator());

        Resource resource = this.queryService.getCarDetailsQuery(session,"a2007ce8-cf09-465b-93f7-36fef971325f");
        assertNotNull(resource);

        assertEquals("Hatchback",resource.getValueMap().get("BodyStyle"));
        assertEquals("/apps/vehicle/components/header/clientlib/resources/carLogo.png",resource.getValueMap().get("ImageUrl"));
        assertEquals("30000",resource.getValueMap().get("Kilometers",String.class));
        assertEquals("2020",resource.getValueMap().get("Year",String.class));
        assertEquals("Automatic",resource.getValueMap().get("Transmission"));
        assertEquals("a2007ce8-cf09-465b-93f7-36fef971325f",resource.getValueMap().get("CarId"));
        assertEquals("4784",resource.getValueMap().get("CarModelId",String.class));
        assertEquals("Roadster",resource.getValueMap().get("CarModelName"));
        assertEquals("Opel",resource.getValueMap().get("BrandName"));
        assertEquals("471",resource.getValueMap().get("BrandId",String.class));
    }

    @Test
    void getCarDetailsQueryInvalidId() {
        resourceList = new ArrayList<>();
        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willReturn(query);
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resourceList.iterator());

        assertThrows(CarNotFoundException.class,
                ()->queryService.getCarDetailsQuery(session,"125"),
                "Car not found");
    }

    @Test
    void getFilteredCarsAll() {
        Resource resource = context.currentResource(Constants.CARS_NODE_LOCATION);
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CARS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());

        List<Resource> list =  new ArrayList<>();
        this.queryService.getFilteredCars(session,"All","All","All").forEachRemaining(list::add);

        assertEquals(27,list.size());
    }

    @Test
    void getFilteredCarsByBrand(){
        Resource resource = context.currentResource("/content/vehicle/carData/filteredCarsByBrand");
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CARS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("BrandId", pg.get(2).getParameters().get("property"));
            assertEquals("449", pg.get(2).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());
        List<Resource> list =  new ArrayList<>();
        this.queryService.getFilteredCars(session,"449","All","All").forEachRemaining(list::add);

        assertEquals(4,list.size());
    }

    @Test
    void getFilteredCarsByBrandAndCarModel(){
        Resource resource = context.currentResource("/content/vehicle/carData/filteredCarsByBrandAndCarModel");
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CARS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("BrandId", pg.get(2).getParameters().get("property"));
            assertEquals("449", pg.get(2).getParameters().get("value"));
            assertEquals("CarModelId", pg.get(3).getParameters().get("property"));
            assertEquals("2081", pg.get(3).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());
        List<Resource> list =  new ArrayList<>();
        this.queryService.getFilteredCars(session,"449","2081","All").forEachRemaining(list::add);

        assertEquals(2,list.size());
    }

    @Test
    void getFilteredCarsByYear(){
        Resource resource = context.currentResource("/content/vehicle/carData/filteredCarsByBrandCarModelAndYear");
        assertNotNull(resource);

        given(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).willAnswer(invocation -> {
            PredicateGroup pg = invocation.getArgument(0);
            assertEquals("path", pg.get(0).getType());
            assertEquals(Constants.CARS_NODE_LOCATION, pg.get(0).getParameters().get("path"));
            assertEquals("property", pg.get(1).getType());
            assertEquals("jcr:primaryType", pg.get(1).getParameters().get("property"));
            assertEquals("nt:unstructured", pg.get(1).getParameters().get("value"));
            assertEquals("BrandId", pg.get(2).getParameters().get("property"));
            assertEquals("449", pg.get(2).getParameters().get("value"));
            assertEquals("CarModelId", pg.get(3).getParameters().get("property"));
            assertEquals("2081", pg.get(3).getParameters().get("value"));
            assertEquals("Year", pg.get(4).getParameters().get("property"));
            assertEquals("2022", pg.get(4).getParameters().get("value"));
            assertEquals("-1", pg.getParameters().get("limit"));
            return query;
        });
        given(query.getResult()).willReturn(searchResult);
        given(searchResult.getResources()).willReturn(resource.getChildren().iterator());
        List<Resource> list =  new ArrayList<>();
        this.queryService.getFilteredCars(session,"449","2081","2022").forEachRemaining(list::add);

        assertEquals(3,list.size());
    }
}