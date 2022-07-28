package com.vehicle.core.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarItem {
    private int carId;
    private String imageUrl;
    private String description;
}
