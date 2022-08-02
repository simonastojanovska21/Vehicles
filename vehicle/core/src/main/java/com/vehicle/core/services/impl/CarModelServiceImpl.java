package com.vehicle.core.services.impl;

import com.vehicle.core.models.CarModel;
import com.vehicle.core.models.exceptions.CarModelNotFoundException;
import com.vehicle.core.services.CarModelService;
import com.vehicle.core.services.QueryService;
import com.vehicle.core.utils.Constants;
import com.vehicle.core.utils.ResourceResolverUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component(service = CarModelService.class)
public class CarModelServiceImpl implements CarModelService {

    private static final Logger log = LoggerFactory.getLogger(CarModelServiceImpl.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private QueryService queryService;

    @Override
    public List<CarModel> getAllCarModels() {
        List<CarModel> carModels=new ArrayList<>();
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            queryService.getAllCarModelsQuery(session).getHits().forEach(each->{
                try {
                    carModels.add(createCarModelFromResource(each.getResource()));
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            log.error("Exception when getting car models from the repository: {}",e.getMessage());
            e.printStackTrace();
        }
        log.info("Returning {} car models",carModels.size());
        return carModels;
    }

    @Override
    public List<CarModel> getCarModelsForBrand(String brandId) {
        List<CarModel> carModels = new ArrayList<>();
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            queryService.getAllCarModelsForBrandQuery(session,brandId).getHits().forEach(each-> {
                try {
                    carModels.add(createCarModelFromResource(each.getResource()));
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            log.error("Exception when getting car models for brand from the repository {}",e.getMessage());
            e.printStackTrace();
        }
        return carModels;
    }

    @Override
    public void addNewCarModelToRepository(int carModelId, String carModelName, int brandId, Session session) throws RepositoryException {
        //Getting the root location for adding car model.
        Node carModelsRootNode =  session.getNode(Constants.CAR_MODELS_NODE_LOCATION);
        if(!carModelsRootNode.hasNode("CarModelId"+carModelId)){
            Node carModel = carModelsRootNode.addNode("CarModel"+carModelId);
            carModel.setProperty("CarModelId",carModelId);
            String carModelNameProp = carModelName.substring(0,1).toUpperCase() + carModelName.toLowerCase().substring(1);
            carModel.setProperty("CarModelName",carModelNameProp);
            carModel.setProperty("BrandId",brandId);
        }
    }

    private CarModel createCarModelFromResource(Resource resource){
        ValueMap properties = resource.adaptTo(ValueMap.class);
        String carModelId = properties.get("CarModelId",String.class);
        String carModelName = properties.get("CarModelName",String.class);
        String brandId = properties.get("BrandId",String.class);
        if(StringUtils.isNotBlank(carModelId) && StringUtils.isNotBlank(carModelName) && StringUtils.isNotBlank(brandId)){
            return new CarModel(Integer.parseInt(carModelId),carModelName,Integer.parseInt(brandId));
        }
        return null;
    }
}
