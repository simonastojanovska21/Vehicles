package com.vehicle.core.services;

import com.vehicle.core.models.Brand;
import org.apache.sling.api.SlingHttpServletRequest;
import org.osgi.annotation.versioning.ProviderType;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

@ProviderType
public interface BrandService {
    /*
    Method used for fetching brands data from JCR
     */
    List<Brand> getAllBrands();

    void addNewBrandToRepository(int brandId, String brandName, Session session) throws RepositoryException;
}
