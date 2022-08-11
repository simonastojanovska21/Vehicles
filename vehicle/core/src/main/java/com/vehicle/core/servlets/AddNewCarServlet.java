package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vehicle.core.models.dto.CarDto;
import com.vehicle.core.models.enums.BodyStyle;
import com.vehicle.core.models.enums.Transmission;
import com.vehicle.core.services.CarService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;


@Component(service = {Servlet.class})
@SlingServletPaths("/bin/addNewCar")
@ServiceDescription("Add new car Servlet")
public class AddNewCarServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(AddNewCarServlet.class);

    @Reference
    private CarService carService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        Transmission[] transmission = Transmission.values();
        BodyStyle[] bodyStyles = BodyStyle.values();
        node.put("transmission", mapper.writeValueAsString(transmission));
        node.put("bodyStyle", mapper.writeValueAsString(bodyStyles));

        resp.getWriter().write(node.toString());
    }

    @Override
    protected void doPost(final SlingHttpServletRequest req,
                          final SlingHttpServletResponse resp) throws ServletException, IOException{
        try {
            log.info("Method call from servlet");
            carService.processAddNewCarPostRequest(req);
            resp.sendRedirect("/content/vehicle/us/en/cars.html");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
