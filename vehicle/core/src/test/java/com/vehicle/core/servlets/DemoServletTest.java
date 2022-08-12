package com.vehicle.core.servlets;

import com.vehicle.core.ConstantsForTesting;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DemoServletTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @InjectMocks
    private DemoServlet demoServlet = new DemoServlet();

    @Mock
    private MockSlingHttpServletRequest request;

    @Mock
    private MockSlingHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = context.request();
        response = context.response();
    }

    @Test
    void doGetShouldReturnJSON() throws ServletException, IOException {
        demoServlet.doGet(request,response);
        assertEquals(ConstantsForTesting.JSON_CONTENT_TYPE,response.getContentType());

    }

    @Test
    void doGetShouldAcceptParameter() throws ServletException, IOException {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name","Simona");
        request.setParameterMap(paramMap);
        demoServlet.doGet(request,response);
        assertEquals("{\"message\":\"greetings Simona\"}",response.getOutputAsString());
    }
}