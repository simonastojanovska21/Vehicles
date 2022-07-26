package com.vehicle.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
@Getter
@AllArgsConstructor
public class Brand {

    private int brandId;
    private String brandName;

}
