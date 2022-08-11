package com.vehicle.core.utils;


import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

/*
Helper class for manipulation with the jcr. The static method is used for creating a resource resolver and using it
in the services.
 */
public class ResourceResolverUtil {

    //Static field representing the sub service name configured previously
    public static final String VEHICLE_SERVICE_USER = "vehiclewriteserviceuser";

    /**
     *Static method used for creating resource resolver so that service user can access the repository
     * @param resourceResolverFactory
     * @return
     * @throws LoginException
     */
    public static ResourceResolver createNewResolver(ResourceResolverFactory resourceResolverFactory) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, VEHICLE_SERVICE_USER);
        return resourceResolverFactory.getServiceResourceResolver(paramMap);
    }
}
