package com.vehicle.core.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

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
