package com.vehicle.core.services;

import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.CarDto;
import com.vehicle.core.models.dto.CarItem;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.osgi.annotation.versioning.ProviderType;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.List;

@ProviderType
public interface CarService {
    /*
    Method for fetching data about all cars in the JCR
     */
    List<Car> getAllCars() throws LoginException;
    /*
    Method used for filtering cars by different categories, all of which are optional.
     */
    List<CarItem> filterCars(String brandId, String carModelId, String year) throws LoginException;
    /*
    Method used for getting data about a specific car, given the carId
     */
    Car getDetailsAboutCar(String carId) throws LoginException;

    /*
    Method for adding new car to JCR
     */
    void addNewCarToRepository(CarDto carDto, Session session) throws RepositoryException;

    /*
    Method for processing the request for adding new car in the repository
     */
    void processAddNewCarPostRequest(SlingHttpServletRequest request) throws IOException, LoginException, RepositoryException;
}
