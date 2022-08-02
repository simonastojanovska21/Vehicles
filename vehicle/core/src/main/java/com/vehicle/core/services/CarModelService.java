package com.vehicle.core.services;

import com.vehicle.core.models.CarModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.annotation.versioning.ProviderType;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

@ProviderType
public interface CarModelService {
    /*
    Method for fetching information about all car models in the JCR
     */
    List<CarModel> getAllCarModels();
    /*
    Method for getting all car models for a specific brand identified with brandId
     */
    List<CarModel> getCarModelsForBrand(String brandId);
    /*
    Method for creating new car model node
     */
    void addNewCarModelToRepository(int carModelId, String carModelName, int brandId, Session session) throws RepositoryException;
}
