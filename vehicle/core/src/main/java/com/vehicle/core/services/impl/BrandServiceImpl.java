package com.vehicle.core.services.impl;

import com.vehicle.core.models.Brand;
import com.vehicle.core.services.BrandService;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

@Component(service = BrandService.class)
public class BrandServiceImpl implements BrandService{
    @Override
    public List<Brand> getAllBrands() {
        return this.createBrandList();
    }

    public List<Brand> createBrandList(){
        List<Brand> brands = new ArrayList<>();
        Brand brand = new Brand(1,"Mercedes Benz");
        brands.add(brand);
        brand = new Brand(2,"Tesla");
        brands.add(brand);
        brand = new Brand(3,"BMW");
        brands.add(brand);
        brand = new Brand(4,"Volkswagen");
        brands.add(brand);
        brand = new Brand(5,"Fiat");
        brands.add(brand);
        brand = new Brand(6,"Audi");
        brands.add(brand);
        brand = new Brand(7,"Ford");
        brands.add(brand);
        brand = new Brand(8,"Honda");
        brands.add(brand);
        brand = new Brand(9,"Peugeot");
        brands.add(brand);
        brand = new Brand(10,"Opel");
        brands.add(brand);
        return brands;
    }
}
