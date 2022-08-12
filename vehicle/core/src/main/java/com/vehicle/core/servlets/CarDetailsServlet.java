package com.vehicle.core.servlets;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.services.CarService;
import com.vehicle.core.services.impl.CarModelServiceImpl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
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
import java.util.List;

@Component(service = {Servlet.class})
@SlingServletPaths("/bin/carDetails")
@ServiceDescription("Car details Servlet")
public class CarDetailsServlet extends SlingSafeMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(CarDetailsServlet.class);


    @Reference
    private CarService carService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String carId = req.getParameter("carId");
            log.info("Request for id: " +carId);
            ObjectMapper mapper = new ObjectMapper();
            String jsonArray = mapper.writeValueAsString(carService.getDetailsAboutCar(carId));
            resp.getWriter().write(jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
