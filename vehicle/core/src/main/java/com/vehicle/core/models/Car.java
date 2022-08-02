package com.vehicle.core.models;

import com.vehicle.core.models.enums.BodyStyle;
import com.vehicle.core.models.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
@Getter
@AllArgsConstructor
public class Car {

    private String carId;
    private int brandId;
    private String brandName;
    private int carModelId;
    private String carModelName;
    private String imageUrl;
    private int year;
    private int kilometers;
    private String transmission;
    private String bodyStyle;

}
