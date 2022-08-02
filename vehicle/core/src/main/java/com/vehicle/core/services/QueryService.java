package com.vehicle.core.services;

import com.day.cq.search.result.SearchResult;
import org.osgi.annotation.versioning.ProviderType;

import javax.jcr.Session;

@ProviderType
public interface QueryService {

    /**
     * Method for querying the JCR for getting all the brands
     */
    SearchResult getAllBrandsQuery(Session session);

    /**
     * Method for querying the JCR for getting all car models
      */
    SearchResult getAllCarModelsQuery(Session session);

    /**
     * Method for query the JCR for getting all the car models for specific brands, identified with brandId
     * @param brandId
     */
    SearchResult getAllCarModelsForBrandQuery(Session session, String brandId);

    /**
     * Method for querying the JCR for getting all the cars
     */
    SearchResult getAllCarsQuery(Session session);

    /**
     * Method for querying the JCR for getting details about car, identified by carId
     * @param carId
     */
    SearchResult getCarDetailsQuery(Session session, String carId);

    /**
     * Method for querying the JCR for getting filtered cars by brandId, carModelId or year
     * @param session
     * @param brandId
     * @param carModelId
     * @param year
     * @return
     */
    SearchResult getFilteredCars(Session session, String brandId, String carModelId, String year);
}
