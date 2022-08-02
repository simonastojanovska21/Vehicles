package com.vehicle.core.services;

import org.osgi.annotation.versioning.ProviderType;

import java.io.IOException;

@ProviderType
public interface HttpClientService {
    /**
     * Method used for sending HTTP request to API for getting car model for brand specified with brand name and
     * returning the result in json format. From the json object returned from the API we are getting the Results object
     * which is array of car models and brands.
     * @param brand
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    String getCarModelsForBrandInJSON(String brand) throws IOException, InterruptedException;
}
