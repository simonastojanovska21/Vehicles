package com.vehicle.core.models;

import com.vehicle.core.models.enums.BodyStyle;
import com.vehicle.core.models.enums.Transmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import java.util.Objects;

@Model(adaptables = Resource.class)
@Getter
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return brandId == car.brandId && carModelId == car.carModelId && year == car.year && kilometers == car.kilometers && carId.equals(car.carId) && brandName.equals(car.brandName) && carModelName.equals(car.carModelName) && imageUrl.equals(car.imageUrl) && transmission.equals(car.transmission) && bodyStyle.equals(car.bodyStyle);
    }

}
