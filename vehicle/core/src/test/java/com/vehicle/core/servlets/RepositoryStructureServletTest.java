package com.vehicle.core.servlets;

import com.vehicle.core.services.RepositoryStructureService;
import com.vehicle.core.services.impl.RepositoryStructureServiceImpl;
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

import javax.jcr.RepositoryException;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.mock;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class RepositoryStructureServletTest {

    public AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @Mock
    private MockSlingHttpServletRequest request;

    @Mock
    private MockSlingHttpServletResponse response;

    @InjectMocks
    private RepositoryStructureServlet repositoryStructureServlet= new RepositoryStructureServlet();

    private final RepositoryStructureService repositoryStructureService = mock(RepositoryStructureServiceImpl.class);

    @BeforeEach
    void setUp() {
        context.registerService(repositoryStructureService);
        request = context.request();
        response = context.response();
    }

    @Test
    void doGet() throws LoginException, RepositoryException, IOException, InterruptedException, ServletException {
        StringBuilder stringBuilder = new StringBuilder();
        willAnswer(invocationOnMock -> {
            stringBuilder.append("Create repository structure method called.");
            return null;
        }).given(repositoryStructureService).createRepositoryStructure();
        willAnswer(invocationOnMock -> {
            stringBuilder.append("Import brand and car models method called.");
            return null;
        }).given(repositoryStructureService).importBrandsAndCarMakes();
        willAnswer(invocationOnMock -> {
            stringBuilder.append("Import cars method called.");
            return null;
        }).given(repositoryStructureService).importCars();
        String expectedResult = "Create repository structure method called.Import brand and car models method called.Import cars method called.";
        repositoryStructureServlet.doGet(request,response);
        assertEquals(expectedResult,stringBuilder.toString());
    }

}