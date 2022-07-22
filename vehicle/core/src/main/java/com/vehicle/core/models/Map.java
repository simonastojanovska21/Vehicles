package com.vehicle.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class)
public class Map {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy= InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;


    @ValueMapValue
    @Default(doubleValues = 41.9981)
    private double lat;

    @ValueMapValue
    @Default(doubleValues = 21.4254)
    private double lon;


    public double getLat() {
        return lat;
    }


    public double getLon() {
        return lon;
    }
}
