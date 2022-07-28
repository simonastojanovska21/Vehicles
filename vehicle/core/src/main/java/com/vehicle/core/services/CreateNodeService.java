package com.vehicle.core.services;

import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface CreateNodeService {
    String createCarDataNode(SlingHttpServletRequest request);
}
