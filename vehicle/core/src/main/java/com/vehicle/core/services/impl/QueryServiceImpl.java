package com.vehicle.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.vehicle.core.services.QueryService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component(service = QueryService.class)
public class QueryServiceImpl implements QueryService{

    @Reference
    private QueryBuilder builder;

    @Override
    public SearchResult getAllBrandsQuery(Session session) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/brands");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }

    @Override
    public SearchResult getAllCarModelsQuery(Session session) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/carModels");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }

    @Override
    public SearchResult getAllCarModelsForBrandQuery(Session session, String brandId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/carModels");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("2_property","BrandId");
        map.put("2_property.value",brandId);
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }

    @Override
    public SearchResult getAllCarsQuery(Session session) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/cars");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }

    @Override
    public SearchResult getCarDetailsQuery(Session session, String carId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/cars");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("2_property","CarId");
        map.put("2_property.value",carId);
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }

    @Override
    public SearchResult getFilteredCars(Session session, String brandId, String carModelId, String year) {
        int counter = 2;
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/cars");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        if(!Objects.equals(brandId, "All")){
            map.put(counter+"_property","BrandId");
            map.put(counter+"_property.value",brandId);
            counter++;
        }
        if(!Objects.equals(carModelId,"All")){
            map.put(counter+"_property","CarModelId");
            map.put(counter+"_property.value",carModelId);
            counter++;
        }
        if(!Objects.equals(year, "All")) {
            map.put(counter+"_property","Year");
            map.put(counter+"_property.value",year);
        }
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult();
    }
}
