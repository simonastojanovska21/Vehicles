package com.vehicle.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class Car {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy= InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @ValueMapValue
    @Default(values = "Mercedes")
    private String brand;

    @ValueMapValue
    @Default(values = "C class")
    private String model;

    @ValueMapValue
    @Default(values = "https://www.la.mercedes-benz.com/en/passengercars/mercedes-benz-cars/models/c-class/saloon-w206/design/line-comparison/_jcr_content/comparisonslider/par/comparisonslide_361878885/exteriorImage.MQ6.12.20210706161723.jpeg")
    private String imageUrl;

    @ValueMapValue
    @Default(intValues = 2020)
    private int year;

    @ValueMapValue
    @Default(intValues = 20000)
    private int kilometers;

//    public Car(String brand, String model, String imageUrl, int year, int kilometers){
//        this.brand = brand;
//        this.model = model;
//        this.imageUrl = imageUrl;
//        this.year = year;
//        this.kilometers = kilometers;
//    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getKilometers() {
        return kilometers;
    }

    public int getYear() {
        return year;
    }

}
