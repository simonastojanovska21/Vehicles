package com.vehicle.core.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.ConstantsForTesting;
import com.vehicle.core.models.Brand;
import com.vehicle.core.services.BrandService;
import com.vehicle.core.services.impl.BrandServiceImpl;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class BrandServletTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private BrandServlet brandServlet = new BrandServlet();

    @Mock
    private MockSlingHttpServletRequest request;

    @Mock
    private MockSlingHttpServletResponse response;

    private final BrandService brandService = mock(BrandServiceImpl.class);
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        context.registerService(brandService);
        request = context.request();
        response = context.response();
    }

    @Test
    void doGet_shouldReturnBrandsList() throws IOException, LoginException, ServletException {
        String jsonData = "[{\"brandId\":504,\"brandName\":\"Smart\"}," +
                "{\"brandId\":584,\"brandName\":\"Porsche\"}," +
                "{\"brandId\":483,\"brandName\":\"Jeep\"}]";
        objectMapper = new ObjectMapper();
        List<Brand> brands = objectMapper.readValue(jsonData,new TypeReference<>() {});
        given(brandService.getAllBrands()).willReturn(brands);
        brandServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
        assertEquals(jsonData,response.getOutputAsString());
    }
}