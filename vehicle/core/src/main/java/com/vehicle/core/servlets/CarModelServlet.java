package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.services.CarModelService;
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
//        selectors = "carModels",
//        extensions="json")
@SlingServletPaths("/bin/carModels")
@ServiceDescription("Car models Servlet")
public class CarModelServlet extends SlingSafeMethodsServlet {

    @Reference
    private CarModelService carModelService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {

        try {
            resp.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            String jsonArray = mapper.writeValueAsString(carModelService.getAllCarModels());
            String brandId = req.getParameter("brandId");
            if(brandId != null){
                jsonArray = mapper.writeValueAsString(carModelService.getCarModelsForBrand(brandId));
            }
            resp.getWriter().write(jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
