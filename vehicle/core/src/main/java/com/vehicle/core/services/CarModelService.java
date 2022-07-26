package com.vehicle.core.services;

import com.vehicle.core.models.CarModel;
import org.osgi.annotation.versioning.ProviderType;

import java.util.List;

@ProviderType
public interface CarModelService {
    List<CarModel> getAllCarModels();
    List<CarModel> getCarModelsForBrand(int brandId);
}
