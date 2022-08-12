package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.ConstantsForTesting;
import com.vehicle.core.services.CarService;
import com.vehicle.core.services.impl.CarServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.*;


@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AddNewCarServletTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    private AddNewCarServlet addNewCarServlet;

    private MockSlingHttpServletRequest request;

    private MockSlingHttpServletResponse response;

    private final CarService carService = mock(CarServiceImpl.class);
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        context.registerService(carService);
        addNewCarServlet = context.registerInjectActivateService(new AddNewCarServlet());
        request = context.request();
        response = context.response();
    }
    @Test
    void doGet_shouldReturnDataForFormForPostRequest() throws ServletException, IOException {
        String expectedResult = "{\"transmission\":\"[\\\"Manual\\\",\\\"Automatic\\\"]\",\"bodyStyle\":\"[\\\"Sedan\\\",\\\"SUV\\\",\\\"Minivan\\\",\\\"Coupe\\\",\\\"Cabriolet\\\",\\\"Hatchback\\\",\\\"Pickup\\\"]\"}";
        objectMapper = new ObjectMapper();
        addNewCarServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());
        assertEquals(expectedResult,response.getOutputAsString());
    }

    @Test
    void doPost() throws LoginException, RepositoryException, IOException, ServletException {
        StringBuilder stringBuilder = new StringBuilder();

        willAnswer(invocation -> {
            stringBuilder.append("Post request for adding new car in repository");
            return null;
        }).given(carService).processAddNewCarPostRequest(any(SlingHttpServletRequest.class));

        String expectedResult = "Post request for adding new car in repository";
        addNewCarServlet.doPost(request,response);
        assertEquals(expectedResult,stringBuilder.toString());
    }

}