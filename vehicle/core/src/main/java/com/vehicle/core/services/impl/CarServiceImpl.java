package com.vehicle.core.services.impl;

import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.CarItem;
import com.vehicle.core.models.enums.BodyStyle;
import com.vehicle.core.models.enums.Transmission;
import com.vehicle.core.models.exceptions.CarNotFoundException;
import com.vehicle.core.services.BrandService;
import com.vehicle.core.services.CarModelService;
import com.vehicle.core.services.CarService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component(service = CarService.class)
public class CarServiceImpl  implements CarService{

    @Reference
    private CarModelService carModelService;

    @Reference
    private BrandService brandService;

    @Override
    public List<Car> getAllCars() {
        return this.createCars();
    }

    @Override
    public List<CarItem> filterCars(String brandId, String carModelId, String year) {
        List<Car> cars = getAllCars();
        if(!Objects.equals(year, "All"))
        {
            cars = cars.stream().filter(car->car.getYear() == Integer.parseInt(year)).collect(Collectors.toList());
        }
        if(!Objects.equals(brandId,"All")){
            if(!Objects.equals(carModelId,"All")){
                cars = cars.stream().filter(car -> car.getCarModelId()==Integer.parseInt(carModelId)).collect(Collectors.toList());
            }
            else {
                cars = cars.stream().filter(car -> car.getBrandId() == Integer.parseInt(brandId)).collect(Collectors.toList());
            }
        }
        return getCarItems(cars);
    }

    @Override
    public Car getDetailsAboutCar(int carId) {
        return this.createCars().stream().filter(car->car.getCarId() == carId).findFirst().orElseThrow(CarNotFoundException::new);
    }

    private List<CarItem> getCarItems(List<Car> cars ){
        return cars.stream().map(car -> new CarItem(car.getCarId(),car.getImageUrl(),
                brandService.getBrandNameForBrandId(car.getBrandId()) +" - "
                        + carModelService.getNameForCarModelId(car.getCarModelId()))).collect(Collectors.toList());
    }

    private List<Car> createCars(){
        List<Car> cars = new ArrayList<>();
        Car car=new Car(1,1,2,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,10000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(2,1,3,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,50000, Transmission.Automatic, BodyStyle.SUV);
        cars.add(car);
        car=new Car(3,1,3,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,20000, Transmission.Automatic, BodyStyle.SUV);
        cars.add(car);
        car=new Car(4,1,4,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,10000, Transmission.Automatic, BodyStyle.Coupe);
        cars.add(car);
        car=new Car(5,2,5,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,20000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(6,2,6,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,5000, Transmission.Automatic, BodyStyle.Cabriolet);
        cars.add(car);
        car=new Car(7,3,7,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,10000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(8,3,7,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2019,10000, Transmission.Automatic, BodyStyle.Cabriolet);
        cars.add(car);
        car=new Car(9,3,9,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,15000, Transmission.Automatic, BodyStyle.Hatchback);
        cars.add(car);
        car=new Car(10,4,10,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,6000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(11,4,10,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,15000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(12,5,13,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic, BodyStyle.Hatchback);
        cars.add(car);
        car=new Car(13,6,17,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2019,20000, Transmission.Automatic, BodyStyle.Pickup);
        cars.add(car);
        car=new Car(14,6,16,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,7000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(15,6,16,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2018,50000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(16,7,19,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,40000, Transmission.Automatic, BodyStyle.Minivan);
        cars.add(car);
        car=new Car(17,8,20,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        car=new Car(18,8,21,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2019,50000, Transmission.Automatic, BodyStyle.Cabriolet);
        cars.add(car);
        car=new Car(19,9,23,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2018,70000, Transmission.Automatic, BodyStyle.Minivan);
        cars.add(car);
        car=new Car(20,10,24,"/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic, BodyStyle.Sedan);
        cars.add(car);
        return cars;
    }
}
