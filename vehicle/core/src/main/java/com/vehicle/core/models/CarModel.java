package com.vehicle.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {
    private int carModelId;
    private String modelName;
    private int brandId;
}
