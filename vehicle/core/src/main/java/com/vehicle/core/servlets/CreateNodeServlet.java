package com.vehicle.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vehicle.core.services.CreateNodeService;
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
@SlingServletPaths("/bin/nodeRepo")
@ServiceDescription("Node Servlet")
public class CreateNodeServlet extends SlingSafeMethodsServlet {
    @Reference
    private CreateNodeService createNodeService;


    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String result = createNodeService.createCarDataNode(req);
        ObjectNode node = mapper.createObjectNode();
        node.put("message",result);
        resp.getWriter().write(node.toString());
    }

}
