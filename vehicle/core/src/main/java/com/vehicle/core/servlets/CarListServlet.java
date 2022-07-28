package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.services.CarService;
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

@Component(service = {Servlet.class})
@SlingServletPaths("/bin/cars")
@ServiceDescription("Cars Servlet")
public class CarListServlet extends SlingSafeMethodsServlet {

    @Reference
    private CarService carService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String jsonArray = mapper.writeValueAsString(carService.filterCars(req.getParameter("brandId"),
                req.getParameter("carModelId"),
                req.getParameter("year")));
        resp.getWriter().write(jsonArray);
    }
}
