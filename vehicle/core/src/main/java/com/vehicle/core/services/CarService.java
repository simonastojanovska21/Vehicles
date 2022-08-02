package com.vehicle.core.services;

import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.CarItem;
import org.osgi.annotation.versioning.ProviderType;

import java.util.List;

@ProviderType
public interface CarService {
    /*
    Method for fetching data about all cars in the JCR
     */
    List<Car> getAllCars();
    /*
    Method used for filtering cars by different categories, all of which are optional.
     */
    List<CarItem> filterCars(String brandId, String carModelId, String year);
    /*
    Method used for getting data about a specific car, given the carId
     */
    Car getDetailsAboutCar(String carId);
}
