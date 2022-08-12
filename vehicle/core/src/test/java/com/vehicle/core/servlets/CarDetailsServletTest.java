package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.ConstantsForTesting;
import com.vehicle.core.models.Car;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class CarDetailsServletTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private CarDetailsServlet carDetailsServlet = new CarDetailsServlet();

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
    void doGet_shouldReturnNullForCarObjectWithoutRequestParameter() throws LoginException, ServletException, IOException {
        given(carService.getDetailsAboutCar(any(String.class))).willReturn(new Car());
        carDetailsServlet.doGet(request,response);
        assertEquals("null",response.getOutputAsString());
    }

    @Test
    void doGet_shouldReturnCarObjectWithIdAsRequestParameter() throws IOException, LoginException, ServletException {
        String jsonData = "{\"carId\":\"c65b272e-c359-4ed6-ae3a-de3f2b5e08ee\",\"brandId\":441,\"brandName\":\"Tesla\",\"carModelId\":10199,\"carModelName\":\"Model x\",\"imageUrl\":\"/apps/vehicle/components/header/clientlib/resources/carLogo.png\",\"year\":2022,\"kilometers\":7000,\"transmission\":\"Automatic\",\"bodyStyle\":\"Sedan\"}";
        objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(jsonData,Car.class);
        given(carService.getDetailsAboutCar(any(String.class))).willReturn(car);
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("carId","c65b272e-c359-4ed6-ae3a-de3f2b5e08ee");
        request.setParameterMap(paramMap);
        carDetailsServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
        assertEquals(jsonData,response.getOutputAsString());
    }

}