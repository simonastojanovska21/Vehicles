package com.vehicle.core.services;

import com.day.cq.search.result.SearchResult;
import org.apache.sling.api.resource.Resource;
import org.osgi.annotation.versioning.ProviderType;

import javax.jcr.Session;
import java.util.Iterator;

@ProviderType
public interface QueryService {

    /**
     * Method for querying the JCR for getting all the brands
     */
    Iterator<Resource> getAllBrandsQuery(Session session);

    /**
     * Method for querying the JCR for getting all car models
      */
    Iterator<Resource> getAllCarModelsQuery(Session session);

    /**
     * Method for query the JCR for getting all the car models for specific brands, identified with brandId
     * @param brandId
     */
    Iterator<Resource> getAllCarModelsForBrandQuery(Session session, String brandId);

    /**
     * Method for querying the JCR for getting all the cars
     */
    Iterator<Resource> getAllCarsQuery(Session session);

    /**
     * Method for querying the JCR for getting details about car, identified by carId
     * @param carId
     */
    Resource getCarDetailsQuery(Session session, String carId);

    /**
     * Method for querying the JCR for getting filtered cars by brandId, carModelId or year
     * @param session
     * @param brandId
     * @param carModelId
     * @param year
     * @return
     */
    Iterator<Resource> getFilteredCars(Session session, String brandId, String carModelId, String year);
}
