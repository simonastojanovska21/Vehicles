package com.vehicle.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.osgi.annotation.versioning.ProviderType;

import javax.jcr.RepositoryException;
import java.io.IOException;

@ProviderType
public interface RepositoryStructureService {
    /**
     * Method used for creating the repository hierarchy
     * @return
     */
    void createRepositoryStructure() throws RepositoryException, LoginException;

    /**
     * Method used for importing data in the repository, with data received from api
     */
    void importBrandsAndCarMakes() throws RepositoryException, IOException, InterruptedException, LoginException;

    /**
     * Method used for inserting data in the repository, with manually writen data, for testing purposes
     */
    void importCars() throws RepositoryException, LoginException;
}
