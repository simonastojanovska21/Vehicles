package com.vehicle.core.services;

import com.vehicle.core.models.Brand;
import org.osgi.annotation.versioning.ProviderType;

import java.util.List;

@ProviderType
public interface BrandService {
    List<Brand> getAllBrands();
    String getBrandNameForBrandId(int brandId);
}
