package com.vehicle.core.services.impl;

import com.vehicle.core.models.Brand;
import com.vehicle.core.models.exceptions.InvalidDataException;
import com.vehicle.core.services.BrandService;
import com.vehicle.core.services.QueryService;
import com.vehicle.core.utils.Constants;
import com.vehicle.core.utils.ResourceResolverUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of service logic for fetching all brands data and details about specific brand
 */
@Component(service = BrandService.class)
public class BrandServiceImpl implements BrandService{

    private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    private QueryService queryService;

    @Override
    public List<Brand> getAllBrands() throws LoginException {
        List<Brand> brands = new ArrayList<>();
        ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
        Session session = resourceResolver.adaptTo(Session.class);
        queryService.getAllBrandsQuery(session).forEachRemaining(each->brands.add(createBrandFromResource(each)));
        log.info("Returning {} brands",brands.size());
        return brands;
    }

    @Override
    public void addNewBrandToRepository(int brandId, String brandName, Session session) throws RepositoryException {
        //Getting the root location for adding the brands.
        Node brandsNodeRootNode = session.getNode(Constants.BRANDS_NODE_LOCATION);
        if(!brandsNodeRootNode.hasNode("Brand"+brandId)){
            Node brandNode = brandsNodeRootNode.addNode("Brand"+brandId);
            brandNode.setProperty("BrandId",brandId);
            String brandNameProp = brandName.substring(0, 1).toUpperCase() + brandName.toLowerCase().substring(1);
            brandNode.setProperty("BrandName", brandNameProp);
        }
    }

    private Brand createBrandFromResource(Resource resource){
        ValueMap properties = resource.adaptTo(ValueMap.class);
        String brandId = properties.get("BrandId",String.class);
        String brandName = properties.get("BrandName",String.class);
        if(StringUtils.isNotBlank(brandId) && StringUtils.isNotBlank(brandName)){
            return new Brand(Integer.parseInt(brandId),brandName);
        }
        else {
            throw new InvalidDataException("Blank fields detected when creating brand object");
        }
    }

}
