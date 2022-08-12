package com.vehicle.core.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarItem {
    private String carId;
    private String imageUrl;
    private String description;
}
