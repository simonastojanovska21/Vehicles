package com.vehicle.core.services.impl;

import com.vehicle.core.services.CreateNodeService;
import com.vehicle.core.utils.ResourceResolverUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(service = CreateNodeService.class)
public class CreateNodeServiceImpl implements CreateNodeService {

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    private static final String rootNodeLocation="/content/vehicle";

    @Override
    public String createCarDataNode(SlingHttpServletRequest request) {
        String nodeCreated= StringUtils.EMPTY;

        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session=resourceResolver.adaptTo(Session.class);
            Node root = session.getNode("/content/vehicle");
            Node adobe = root.addNode("carData","nt:unstructured");
            Node day = adobe.addNode("day");
            day.setProperty("message", "Adobe CQ is part of the Adobe Digital Marketing Suite!");
            nodeCreated="okay";
            session.save();
            session.logout();
        } catch (LoginException | RepositoryException e) {
            nodeCreated="problem";
            e.printStackTrace();
        }

        return nodeCreated;
    }
}
