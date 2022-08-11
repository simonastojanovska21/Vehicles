package com.vehicle.core.servlets;

import com.vehicle.core.services.RepositoryStructureService;
import com.vehicle.core.services.impl.RepositoryStructureServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

//Servlet with doGet method for creating the repository structure for the project and importing data.
@Component(service = {Servlet.class})
@SlingServletPaths("/bin/repositoryStructure")
@ServiceDescription("Repository Structure Servlet")
public class RepositoryStructureServlet extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(RepositoryStructureServlet.class);

    @Reference
    private RepositoryStructureService repositoryStructureService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        try {
            repositoryStructureService.createRepositoryStructure();
            repositoryStructureService.importBrandsAndCarMakes();
            repositoryStructureService.importCars();
        }catch (Exception e){
            log.error("Exception when creating the repository structure: {}",e.getMessage());
        }
    }
}
