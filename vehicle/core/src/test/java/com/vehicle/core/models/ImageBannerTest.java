package com.vehicle.core.models;

import com.vehicle.core.testcontext.AppAemContext;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
class ImageBannerTest {

    private final AemContext context = AppAemContext.newAemContext();
    private ImageBanner imageBanner;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(ImageBanner.class);
        context.load().json("/com/vehicle/core/models/banner.json","/content");
    }

    @Test
    void getBannerText() {
        imageBanner = Objects.requireNonNull(context.currentResource("/content/imagebanner")).adaptTo(ImageBanner.class);
        assertNotNull(imageBanner);
        String expected = "Need a car?";
        assertEquals(expected,imageBanner.getBannerText());
    }

    @Test
    void getButtonText() {
        imageBanner = Objects.requireNonNull(context.currentResource("/content/imagebanner")).adaptTo(ImageBanner.class);
        assertNotNull(imageBanner);
        String expected = "Shop now";
        assertEquals(expected,imageBanner.getButtonText());
    }

    @Test
    void getButtonRedirect() {
        imageBanner = Objects.requireNonNull(context.currentResource("/content/imagebanner")).adaptTo(ImageBanner.class);
        assertNotNull(imageBanner);
        String expected = "/content/vehicle/us/en/cars";
        assertEquals(expected,imageBanner.getButtonRedirect());
    }
}