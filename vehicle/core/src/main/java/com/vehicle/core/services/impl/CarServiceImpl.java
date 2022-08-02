package com.vehicle.core.services.impl;

import com.day.cq.search.result.Hit;
import com.vehicle.core.models.Car;
import com.vehicle.core.models.CarModel;
import com.vehicle.core.models.dto.CarItem;
import com.vehicle.core.models.enums.BodyStyle;
import com.vehicle.core.models.enums.Transmission;
import com.vehicle.core.models.exceptions.CarNotFoundException;
import com.vehicle.core.services.BrandService;
import com.vehicle.core.services.CarModelService;
import com.vehicle.core.services.CarService;
import com.vehicle.core.services.QueryService;
import com.vehicle.core.utils.ResourceResolverUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component(service = CarService.class)
public class CarServiceImpl  implements CarService{

    private static final Logger log = LoggerFactory.getLogger(CarModelServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private QueryService queryService;

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try{
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            queryService.getAllCarsQuery(session).getHits().forEach(each->{
                try {
                    cars.add(createCarFromResource(each.getResource()));
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            log.error("Exception when getting cars from the repository: {}",e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<CarItem> filterCars(String brandId, String carModelId, String year) {
        List<CarItem> filteredCars = new ArrayList<>();
        try{
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            queryService.getFilteredCars(session,brandId,carModelId,year).getHits().forEach(each->{
                try {
                    filteredCars.add(createCarItemFromResource(each.getResource()));
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            log.error("Exception when getting cars from the repository: {}",e.getMessage());
            e.printStackTrace();
        }
        return filteredCars;
    }

    @Override
    public Car getDetailsAboutCar(String carId) {
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            Hit hit = queryService.getCarDetailsQuery(session,carId).getHits().stream().findFirst().orElseThrow(CarNotFoundException::new);
            return createCarFromResource(hit.getResource());
        }catch (Exception e){
            log.error("Exception when getting details about car from the repository: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
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
        return null;
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
        if(StringUtils.isNotBlank(carId) && StringUtils.isNotBlank(imageUrl)){
            return new CarItem(carId,imageUrl,description);
        }
        return null;
    }

}
