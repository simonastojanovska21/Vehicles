package com.vehicle.core.services.impl;

import com.day.cq.search.result.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.CarDto;
import com.vehicle.core.models.dto.CarItem;
import com.vehicle.core.models.exceptions.CarNotFoundException;
import com.vehicle.core.models.exceptions.InvalidDataException;
import com.vehicle.core.models.exceptions.NodeAlreadyExistException;
import com.vehicle.core.services.CarService;
import com.vehicle.core.services.QueryService;
import com.vehicle.core.utils.Constants;
import com.vehicle.core.utils.ResourceResolverUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component(service = CarService.class)
public class CarServiceImpl  implements CarService{

    private static final Logger log = LoggerFactory.getLogger(CarModelServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private QueryService queryService;

    @Override
    public List<Car> getAllCars() throws LoginException {
        List<Car> cars = new ArrayList<>();
        ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
        Session session = resourceResolver.adaptTo(Session.class);
        queryService.getAllCarsQuery(session).forEachRemaining(each->cars.add(createCarFromResource(each)));
        return cars;
    }

    @Override
    public List<CarItem> filterCars(String brandId, String carModelId, String year) throws LoginException {
        List<CarItem> filteredCars = new ArrayList<>();
        ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
        Session session = resourceResolver.adaptTo(Session.class);
        queryService.getFilteredCars(session,brandId,carModelId,year).forEachRemaining(each->
                filteredCars.add(createCarItemFromResource(each)));
        return filteredCars;
    }

    @Override
    public Car getDetailsAboutCar(String carId) throws LoginException {
        ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
        Session session = resourceResolver.adaptTo(Session.class);
        Resource resource = queryService.getCarDetailsQuery(session,carId);
        return createCarFromResource(resource);
    }

    @Override
    public void addNewCarToRepository(CarDto carDto, Session session) throws RepositoryException {
        Car car = new Car(UUID.randomUUID().toString(),carDto.getBrandId(),carDto.getBrandName(),carDto.getCarModelId(),
                carDto.getCarModelName(),carDto.getImageUrl(),carDto.getYear(),
                carDto.getKilometers(),carDto.getTransmission(), carDto.getBodyStyle());
        addCarNodeToRepository(car,session);
    }

    @Override
    public void processAddNewCarPostRequest(SlingHttpServletRequest request) throws IOException, LoginException, RepositoryException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonCarDto = request.getReader().lines().collect(Collectors.joining());
        CarDto carDto = mapper.readValue(jsonCarDto,CarDto.class);
        ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
        Session session = resourceResolver.adaptTo(Session.class);
        this.addNewCarToRepository(carDto,session);
        session.save();
    }

    /*
    Private method for creating car object from resource
     */
    private Car createCarFromResource(Resource resource){
        ValueMap properties = resource.adaptTo(ValueMap.class);
        String carId = properties.get("CarId",String.class);
        String brandId = properties.get("BrandId", String.class);
        String brandName = properties.get("BrandName",String.class);
        String carModelId=properties.get("CarModelId",String.class);
        String carModelName = properties.get("CarModelName",String.class);
        String imageUrl = properties.get("ImageUrl",String.class);
        String year = properties.get("Year",String.class);
        String kilometers = properties.get("Kilometers",String.class);
        String transmission = properties.get("Transmission",String.class);
        String bodyStyle = properties.get("BodyStyle",String.class);
        if(StringUtils.isNotBlank(carId) &&  StringUtils.isNotBlank(brandId)  && StringUtils.isNotBlank(brandName)
                && StringUtils.isNotBlank(carModelName) &&  StringUtils.isNotBlank(carModelId)
                && StringUtils.isNotBlank(imageUrl) && StringUtils.isNotBlank(year) && StringUtils.isNotBlank(kilometers)
                && StringUtils.isNotBlank(transmission) && StringUtils.isNotBlank(bodyStyle)){
            return new Car(carId,Integer.parseInt(brandId),brandName,Integer.parseInt(carModelId),carModelName,imageUrl,
                    Integer.parseInt(year), Integer.parseInt(kilometers),transmission,bodyStyle);
        }
        else {
            throw new InvalidDataException("Blank fields detected when creating car item");
        }
    }

    /*
    Private method for creating car item object from resource
     */
    private CarItem createCarItemFromResource(Resource resource){
        ValueMap properties = resource.adaptTo(ValueMap.class);
        String carId = properties.get("CarId",String.class);
        String imageUrl = properties.get("ImageUrl",String.class);
        String brandName = properties.get("BrandName",String.class);
        String carModelName = properties.get("CarModelName",String.class);
        String description = brandName + " - " + carModelName;
        if(StringUtils.isNotBlank(carId) && StringUtils.isNotBlank(imageUrl) && StringUtils.isNotBlank(description)){
            return new CarItem(carId,imageUrl,description);
        }
        else {
            throw new InvalidDataException("Blank fields detected when creating car item");
        }
    }

    /*
    Private method for adding new node in the repository under vehicle/content/carData/cars
     */
    private void addCarNodeToRepository(Car car, Session session) throws RepositoryException {
        Node carsNode = session.getNode(Constants.CARS_NODE_LOCATION);
        Node carNode = carsNode.addNode("Car-"+car.getCarId());
        carNode.setProperty("CarId",car.getCarId());
        carNode.setProperty("BrandId",car.getBrandId());
        carNode.setProperty("BrandName",car.getBrandName());
        carNode.setProperty("CarModelId", car.getCarModelId());
        carNode.setProperty("CarModelName",car.getCarModelName());
        carNode.setProperty("ImageUrl",car.getImageUrl());
        carNode.setProperty("Year",car.getYear());
        carNode.setProperty("Kilometers",car.getKilometers());
        carNode.setProperty("Transmission",car.getTransmission());
        carNode.setProperty("BodyStyle",car.getBodyStyle());
    }

}
