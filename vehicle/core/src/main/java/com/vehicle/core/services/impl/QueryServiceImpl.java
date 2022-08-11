package com.vehicle.core.services.impl;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.vehicle.core.models.exceptions.CarNotFoundException;
import com.vehicle.core.models.exceptions.NonUniqueIdException;
import com.vehicle.core.services.QueryService;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import java.util.*;
import java.util.stream.Collectors;

@Component(service = QueryService.class)
public class QueryServiceImpl implements QueryService{

    @Reference
    private QueryBuilder builder;

    @Override
    public Iterator<Resource> getAllBrandsQuery(Session session) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/brands");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult().getResources();
    }

    @Override
    public Iterator<Resource> getAllCarModelsQuery(Session session) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/carModels");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult().getResources();
    }

    @Override
    public Iterator<Resource> getAllCarModelsForBrandQuery(Session session, String brandId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/carModels");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("2_property","BrandId");
        map.put("2_property.value",brandId);
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult().getResources();
    }

    @Override
    public Iterator<Resource> getAllCarsQuery(Session session) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/cars");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult().getResources();
    }

    @Override
    public Resource getCarDetailsQuery(Session session, String carId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/cars");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        map.put("2_property","CarId");
        map.put("2_property.value",carId);
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        SearchResult searchResult = query.getResult();
        if(!searchResult.getResources().hasNext())
            throw new CarNotFoundException("Car not found");
        Resource resource = searchResult.getResources().next();
        if(searchResult.getResources().hasNext())
            throw new NonUniqueIdException("Car node does not have unique id");
        return resource;
    }

    @Override
    public Iterator<Resource> getFilteredCars(Session session, String brandId, String carModelId, String year) {
        //List<Resource> allCars = new ArrayList<>();
        //this.getAllCarsQuery(session).forEachRemaining(allCars::add);

        int counter = 2;
        Map<String, String> map = new HashMap<String, String>();
        map.put("path","/content/vehicle/carData/cars");
        map.put("1_property","jcr:primaryType");
        map.put("1_property.value","nt:unstructured");
        if(!Objects.equals(brandId, "All")){
            //allCars = allCars.stream().filter(each-> Objects.equals(each.getValueMap().get("BrandId", String.class), brandId)).collect(Collectors.toList());
            map.put(counter+"_property","BrandId");
            map.put(counter+"_property.value",brandId);
            counter++;
        }
        if(!Objects.equals(carModelId,"All")){
            //allCars = allCars.stream().filter(each-> Objects.equals(each.getValueMap().get("CarModelId", String.class), carModelId)).collect(Collectors.toList());
            map.put(counter+"_property","CarModelId");
            map.put(counter+"_property.value",carModelId);
            counter++;
        }
        if(!Objects.equals(year, "All")) {
            //allCars = allCars.stream().filter(each-> Objects.equals(each.getValueMap().get("Year", String.class), year)).collect(Collectors.toList());

            map.put(counter+"_property","Year");
            map.put(counter+"_property.value",year);
        }
        map.put("p.limit","-1");
        Query query = builder.createQuery(PredicateGroup.create(map), session);
        return query.getResult().getResources();
    }
}
