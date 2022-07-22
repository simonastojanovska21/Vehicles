package com.vehicle.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class)
public class ImageBanner {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy= InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @ValueMapValue
    @Default(values = "")
    private String bannerText;

    @ValueMapValue
    @Default(values = "")
    private String buttonText;

    @ValueMapValue
    @Default(values = "")
    private String buttonRedirect;

    public String getBannerText() {
        return bannerText;
    }

    public String getButtonText(){return  buttonText;}

    public String getButtonRedirect(){return buttonRedirect;}

}
