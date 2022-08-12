package com.vehicle.core.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.ConstantsForTesting;
import com.vehicle.core.models.dto.CarItem;
import com.vehicle.core.services.CarService;
import com.vehicle.core.services.impl.CarServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CarListServletTest {
    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private CarListServlet carListServlet = new CarListServlet();

    @Mock
    private MockSlingHttpServletRequest request;

    @Mock
    private MockSlingHttpServletResponse response;

    private final CarService carService = mock(CarServiceImpl.class);
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        context.registerService(carService);
        request = context.request();
        response = context.response();
    }

    @Test
    void doGet_shouldReturnJSON() throws LoginException, ServletException, IOException {
        given(carService.filterCars(any(String.class),any(String.class),any(String.class))).willReturn(new ArrayList<>());
        carListServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
    }

    @Test
    void doGet_shouldReturnEmptyCarItemsListWithoutRequestParameters() throws IOException, LoginException, ServletException {
        String jsonData = "[{\"carId\":\"c65b272e-c359-4ed6-ae3a-de3f2b5e08ee\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"description\":\"Tesla - Model x\"}," +
                "{\"carId\":\"f31704cc-faa0-4b74-baa7-fe1c44cd5800\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"description\":\"Jeep - Compass\"}," +
                "{\"carId\":\"988ed09b-7cba-4b74-9310-75afe9bb88f6\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"description\":\"Porsche - Panamera\"}]";
        objectMapper = new ObjectMapper();
        List<CarItem> carItems = objectMapper.readValue(jsonData, new TypeReference<>() {});
        given(carService.filterCars(any(String.class),any(String.class),any(String.class))).willReturn(carItems);
        carListServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
        assertEquals("[]",response.getOutputAsString());
    }

    @Test
    void doGet_shouldReturnCarItemsListWithRequestParameters() throws IOException, LoginException, ServletException {
        String jsonData = "[{\"carId\":\"c65b272e-c359-4ed6-ae3a-de3f2b5e08ee\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"description\":\"Tesla - Model x\"}," +
                "{\"carId\":\"f31704cc-faa0-4b74-baa7-fe1c44cd5800\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"description\":\"Jeep - Compass\"}," +
                "{\"carId\":\"988ed09b-7cba-4b74-9310-75afe9bb88f6\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"description\":\"Porsche - Panamera\"}]";
        objectMapper = new ObjectMapper();
        List<CarItem> carItems = objectMapper.readValue(jsonData, new TypeReference<>() {});
        given(carService.filterCars(any(String.class),any(String.class),any(String.class))).willReturn(carItems);
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("brandId","All");
        paramMap.put("carModelId","All");
        paramMap.put("year","All");
        request.setParameterMap(paramMap);
        carListServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
        assertEquals(jsonData,response.getOutputAsString());
    }

}