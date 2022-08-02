package com.vehicle.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface RepositoryStructureService {
    /**
     * Method used for creating the repository hierarchy
     * @param request
     * @return
     */
    void createRepositoryStructure(SlingHttpServletRequest request);

    /**
     * Method used for importing data in the repository, with data received from api
     * @param request
     */
    void importBrandsAndCarMakes(SlingHttpServletRequest request);

    /**
     * Method used for inserting data in the repository, with manually writen data, for testing purposes
     * @param request
     */
    void importCars(SlingHttpServletRequest request);
}
