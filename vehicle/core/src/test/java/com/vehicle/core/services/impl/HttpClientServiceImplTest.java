package com.vehicle.core.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.models.dto.BrandCarModel;
import com.vehicle.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class HttpClientServiceImplTest {

    private final AemContext context = AppAemContext.newAemContext();

    private final HttpClientServiceImpl httpClientService = new HttpClientServiceImpl();

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getCarModelsForBrandInJSON_randomBrand() throws IOException, InterruptedException {
        String brand = "sdf";
        String response = httpClientService.getCarModelsForBrandInJSON(brand);
        assertNotNull(response);
        assertEquals(0,mapper.readValue(response, new TypeReference<List<BrandCarModel>>(){}).size());
    }

    @Test
    void getCarModelsForBrandInJSON_brand() throws IOException, InterruptedException {
        String brand = "BMW";
        String response = httpClientService.getCarModelsForBrandInJSON(brand);
        assertEquals(242,mapper.readValue(response, new TypeReference<List<BrandCarModel>>(){}).size());
    }
}