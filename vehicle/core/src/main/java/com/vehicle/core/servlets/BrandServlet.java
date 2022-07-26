package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.services.BrandService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = {Servlet.class})
//@SlingServletResourceTypes(
//        resourceTypes="vehicle/components/page",
//        methods= HttpConstants.METHOD_GET,
//        selectors = "brands",
//        extensions="json")
@SlingServletPaths("/bin/brands")
@ServiceDescription("Brands Servlet")
public class BrandServlet extends SlingSafeMethodsServlet {

    @Reference
    private BrandService brandService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String jsonArray = mapper.writeValueAsString(brandService.getAllBrands());
        resp.getWriter().write(jsonArray);
    }
}
