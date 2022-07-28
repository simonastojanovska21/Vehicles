package com.vehicle.core.servlets;

import com.vehicle.core.services.RepositoryStructureService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

//Servlet with doGet method for creating the repository structure for the project and importing data.
@Component(service = {Servlet.class})
@SlingServletPaths("/bin/repositoryStructure")
@ServiceDescription("Repository Structure Servlet")
public class RepositoryStructureServlet extends SlingSafeMethodsServlet {

    @Reference
    private RepositoryStructureService repositoryStructureService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        repositoryStructureService.createRepositoryStructure(req);
        repositoryStructureService.importBrandsAndCarMakes(req);
    }
}
