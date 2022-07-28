package com.vehicle.core.services;

import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.CarItem;
import org.osgi.annotation.versioning.ProviderType;

import java.util.List;

@ProviderType
public interface CarService {
    List<Car> getAllCars();
    List<CarItem> filterCars(String brandId, String carModelId, String year);
    Car getDetailsAboutCar(int carId);
}
