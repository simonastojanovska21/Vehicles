package com.vehicle.core.services.impl;

import com.vehicle.core.models.CarModel;
import com.vehicle.core.services.CarModelService;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component(service = CarModelService.class)
public class CarModelServiceImpl implements CarModelService {
    @Override
    public List<CarModel> getAllCarModels() {
        return createCarModels();
    }

    @Override
    public List<CarModel> getCarModelsForBrand(int brandId) {
        return createCarModels().stream().filter(each->each.getBrandId()==brandId).collect(Collectors.toList());
    }

    private List<CarModel> createCarModels(){
        List<CarModel> carModels = new ArrayList<>();
        CarModel carModel = new CarModel(1,"C-Class",1);
        carModels.add(carModel);
        carModel = new CarModel(2,"E-Class",1);
        carModels.add(carModel);
        carModel = new CarModel(3,"GLA-Class",1);
        carModels.add(carModel);
        carModel = new CarModel(4,"AMG GT",1);
        carModels.add(carModel);
        carModel = new CarModel(5,"Model 3",2);
        carModels.add(carModel);
        carModel = new CarModel(6,"Model S",2);
        carModels.add(carModel);
        carModel = new CarModel(7,"X5",3);
        carModels.add(carModel);
        carModel = new CarModel(8,"M5",3);
        carModels.add(carModel);
        carModel = new CarModel(9,"330i",3);
        carModels.add(carModel);
        carModel = new CarModel(10,"Golf",4);
        carModels.add(carModel);
        carModel = new CarModel(11,"Touareg",4);
        carModels.add(carModel);
        carModel = new CarModel(12,"Passat",4);
        carModels.add(carModel);
        carModel = new CarModel(13,"500",5);
        carModels.add(carModel);
        carModel = new CarModel(14,"Ducato",5);
        carModels.add(carModel);
        carModel = new CarModel(15,"A4",6);
        carModels.add(carModel);
        carModel = new CarModel(16,"A6",6);
        carModels.add(carModel);
        carModel = new CarModel(17,"RS6",6);
        carModels.add(carModel);
        carModel = new CarModel(18,"Focus",7);
        carModels.add(carModel);
        carModel = new CarModel(19,"Mustang",7);
        carModels.add(carModel);
        carModel = new CarModel(20,"Accord",8);
        carModels.add(carModel);
        carModel = new CarModel(21,"Civic",8);
        carModels.add(carModel);
        carModel = new CarModel(22,"505",9);
        carModels.add(carModel);
        carModel = new CarModel(23,"405",9);
        carModels.add(carModel);
        carModel = new CarModel(24,"Ampera",10);
        carModels.add(carModel);
        return carModels;
    }
}
