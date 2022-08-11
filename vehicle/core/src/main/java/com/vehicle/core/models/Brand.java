package com.vehicle.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @ValueMapValue
    private int brandId;

    @ValueMapValue
    private String brandName;

}
