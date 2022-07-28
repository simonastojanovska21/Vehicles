package com.vehicle.core.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Helper model used for creating java object from the json object returned from the api
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandCarModel {

    @JsonProperty("Make_ID")
    private int brandId;

    @JsonProperty("Make_Name")
    private String brandName;

    @JsonProperty("Model_ID")
    private int carModelId;

    @JsonProperty("Model_Name")
    private String carModelName;

}
