package com.vehicle.core.models;

import com.vehicle.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(AemContextExtension.class)
public class MapDefaultTest {
    private final AemContext context = AppAemContext.newAemContext();
    private Map map;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(Map.class);
        context.load().json("/com/vehicle/core/models/MapDefaultValues.json","/content");
    }

    @Test
    void getLat() {
        map = Objects.requireNonNull(context.currentResource("/content/map")).adaptTo(Map.class);
        double expected = 41.9981;
        assertNotNull(map);
        assertEquals(expected,map.getLat());
    }


    @Test
    void getLon() {
        map = Objects.requireNonNull(context.currentResource("/content/map")).adaptTo(Map.class);
        double expected = 21.4254;
        assertNotNull(map);
        assertEquals(expected,map.getLon());
    }
}
