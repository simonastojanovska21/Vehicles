package com.vehicle.core.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.ConstantsForTesting;
import com.vehicle.core.models.CarModel;
import com.vehicle.core.models.dto.BrandCarModel;
import com.vehicle.core.services.CarModelService;
import com.vehicle.core.services.impl.CarModelServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
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

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CarModelServletTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private CarModelServlet carModelServlet = new CarModelServlet();

    @Mock
    private MockSlingHttpServletRequest request;

    @Mock
    private MockSlingHttpServletResponse response;

    private final CarModelService carModelService = mock(CarModelServiceImpl.class);
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        context.registerService(carModelService);
        request = context.request();
        response = context.response();
    }

    @Test
    void doGet_shouldReturnJSON() throws ServletException, IOException, LoginException {
        given(carModelService.getAllCarModels()).willReturn(new ArrayList<>());
        carModelServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
    }

    @Test
    void doGet_shouldReturnCarModelsListInJSON() throws ServletException, IOException, LoginException {
        String jsonData = "[{\"carModelId\":3680,\"modelName\":\"Magnum\",\"brandId\":476}," +
                "{\"carModelId\":1940,\"modelName\":\"Nitro\",\"brandId\":476}," +
                "{\"carModelId\":26625,\"modelName\":\"R 1250 gs\",\"brandId\":452}," +
                "{\"carModelId\":1949,\"modelName\":\"Grand cherokee\",\"brandId\":483}," +
                "{\"carModelId\":5238,\"modelName\":\"328is\",\"brandId\":452}]";
        objectMapper = new ObjectMapper();
        List<CarModel> carModelList = objectMapper.readValue(jsonData, new TypeReference<>() {});
        given(carModelService.getAllCarModels()).willReturn(new ArrayList<>(carModelList));
        carModelServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
        assertEquals(jsonData,response.getOutputAsString());
    }

    @Test
    void doGet_shouldReturnCarModelsForBrandIdGivenAsParameter() throws IOException, LoginException, ServletException {
        String jsonData = "[{\"carModelId\":3680,\"modelName\":\"Magnum\",\"brandId\":476}," +
                "{\"carModelId\":1940,\"modelName\":\"Nitro\",\"brandId\":476}]";
        objectMapper = new ObjectMapper();
        List<CarModel> carModelList = objectMapper.readValue(jsonData, new TypeReference<>() {});
        given(carModelService.getCarModelsForBrand(any(String.class))).willReturn(carModelList);
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("brandId","476");
        request.setParameterMap(paramMap);
        carModelServlet.doGet(request,response);
        assertEquals(jsonData,response.getOutputAsString());
    }
}