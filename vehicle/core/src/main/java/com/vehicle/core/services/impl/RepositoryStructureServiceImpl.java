package com.vehicle.core.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.models.Car;
import com.vehicle.core.models.dto.BrandCarModel;
import com.vehicle.core.models.enums.BodyStyle;
import com.vehicle.core.models.enums.Transmission;
import com.vehicle.core.services.BrandService;
import com.vehicle.core.services.CarModelService;
import com.vehicle.core.services.HttpClientService;
import com.vehicle.core.services.RepositoryStructureService;
import com.vehicle.core.utils.Constants;
import com.vehicle.core.utils.ResourceResolverUtil;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of service logic for creating the repository structure.
 * It implements 1 method from the interface and uses private methods for creating the structure
 */
@Component(service = RepositoryStructureService.class)
public class RepositoryStructureServiceImpl implements RepositoryStructureService{

    private static final Logger log = LoggerFactory.getLogger(RepositoryStructureServiceImpl.class);

    private static final List<String> brands = List.of("TESLA","MERCEDES-BENZ","BMW","OPEL","VOLKSWAGEN","JEEP","FIAT",
            "HYUNDAI", "SMART","AUDI","BENTLEY","PORSCHE","LOTUS","BUGATTI","DODGE");

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Reference
    private HttpClientService httpClientService;

    @Reference
    private CarModelService carModelService;

    @Reference
    private BrandService brandService;

    @Override
    public void createRepositoryStructure(SlingHttpServletRequest request) {
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            createCarDataNode(session);
            session.save();
            session.logout();
        }catch (RepositoryException | LoginException e){
            log.error("Exception when creating the repository structure: {}",e.getMessage());
        }
    }

    @Override
    public void importBrandsAndCarMakes(SlingHttpServletRequest request) {
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            importDataFromApi(session);
            session.save();
            session.logout();
        }catch (Exception e){
            log.error("Exception when creating the repository structure: {}",e.getMessage());
        }
    }

    @Override
    public void importCars(SlingHttpServletRequest request) {
        try {
            ResourceResolver resourceResolver = ResourceResolverUtil.createNewResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            importCarsData(session);
            session.save();
            session.logout();
        }catch (Exception e){
            log.error("Exception when adding cars data: {}",e.getMessage());
        }
    }

    /**
     * Private method used for creating the repository structure. Under /content/vehicle we create carData node.
     * Inside the carData node we have nodes for brands, carModels and Cars which can be populated with data.
     * @param session
     * @throws RepositoryException
     */
    private void createCarDataNode(Session session) throws RepositoryException {
        Node root = session.getNode(Constants.ROOT_NODE_LOCATION);

        if(!root.hasNode("carData")){
            Node carData = root.addNode("carData",Constants.NT_UNSTRUCTURED);
            carData.addNode("brands", Constants.NT_UNSTRUCTURED);
            carData.addNode("carModels",Constants.NT_UNSTRUCTURED);
            carData.addNode("cars",Constants.NT_UNSTRUCTURED);
            log.info("Created car data root node, brands node, car models node and cars nod");
        }
        else{
            log.info("Node already exists");
        }
    }

    /**
     * Private method used for populating the jcr with brand and car model data. The data is obtained from vehicle api,
     * the results are parsed, For each brand, new node is added under /content/vehicle/carData/brands. The name of the
     * node is "Brand" followed by brand id returned from the api. The properties are brandId and brandName.
     * For each car model new node is created under /content/vehicle/carData/carModels. Name of each node is "CarModel"
     * followed by car model id from the api and the properties are carModelId, carModelName and brandId (the brand of
     * the car model);
     * Given the fact that this is simple project I have created list of car brands for which car models will be obtained.
     * @param session
     */
    private void importDataFromApi(Session session) throws IOException, InterruptedException, RepositoryException {
         brands.forEach(brand->{
            try {
                String jsonResults = httpClientService.getCarModelsForBrandInJSON(brand);
                addBrandAndCarModelNodesToRepository(session, jsonResults, brand);
            }catch (Exception e){
                log.error("Exception when calling the api: {}",e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Helper method of importDataFromApi used for adding the nodes in the repository as specified earlier
     * @param session
     * @param jsonResults
     */
    private void addBrandAndCarModelNodesToRepository(Session session, String jsonResults, String brand) throws RepositoryException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        //Mapping the json array to List of BrandCarModel object
        List <BrandCarModel> brandCarModels = mapper.readValue(jsonResults, new TypeReference<List<BrandCarModel>>(){});
        //For each BrandCarModel object we check if there is node for the brand. If not, node for brand data is created
        //Finally node for car model data is created
        brandCarModels.forEach(each->{
            if(each.getBrandName().equalsIgnoreCase(brand)){
                try {
                    brandService.addNewBrandToRepository(each.getBrandId(),each.getBrandName(),session);
                    carModelService.addNewCarModelToRepository(each.getCarModelId(),each.getCarModelName(),each.getBrandId(),session);
                } catch (RepositoryException e) {
                    log.error("Exception when adding the nodes in the repository: {}",e.getMessage());
                }
            }
        });
        log.info("Data saved in repository");
    }

    /**
     * Helper method for reading data about cars stored in list and adding it to JCR under /content/vehicle/carData/cars
     * @param session
     * @throws RepositoryException
     */
    private void importCarsData(Session session) throws RepositoryException {
        Node carsNode = session.getNode(Constants.CARS_NODE_LOCATION);
        getCarsList().forEach(car -> {
            try{
                Node carNode = carsNode.addNode("Car"+car.getCarId());
                carNode.setProperty("CarId",car.getCarId());
                carNode.setProperty("BrandId",car.getBrandId());
                carNode.setProperty("BrandName",car.getBrandName());
                carNode.setProperty("CarModelId", car.getCarModelId());
                carNode.setProperty("CarModelName",car.getCarModelName());
                carNode.setProperty("ImageUrl",car.getImageUrl());
                carNode.setProperty("Year",car.getYear());
                carNode.setProperty("Kilometers",car.getKilometers());
                carNode.setProperty("Transmission",car.getTransmission());
                carNode.setProperty("BodyStyle",car.getBodyStyle());
            }catch (Exception e){
                log.error("Exception when adding cars data: {}",e.getMessage());
                e.printStackTrace();
            }
        });
        log.info("Data saved in repository");
    }

    //Method for creating cars test data
    private List<Car> getCarsList(){
        List<Car> cars = new ArrayList<>();
        Car car=new Car(UUID.randomUUID().toString(),449,"Mercedes-benz",2081,"E-class","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,10000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),449,"Mercedes-benz",5886, "Gle-class","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,50000, Transmission.Automatic.toString(), BodyStyle.SUV.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),449,"Mercedes-benz",2082,"Cls-class","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,20000, Transmission.Automatic.toString(), BodyStyle.SUV.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),449,"Mercedes-benz",2079,"Sl-class","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,10000, Transmission.Automatic.toString(), BodyStyle.Coupe.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),584,"Porsche",7895,"Cayenne","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,20000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),584,"Porsche",7994,"Panamera","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,5000, Transmission.Automatic.toString(), BodyStyle.Cabriolet.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),483,"Jeep",1946,"Compass","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,10000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),483,"Jeep",1947,"Patriot","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2019,10000, Transmission.Automatic.toString(), BodyStyle.Cabriolet.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),504,"Smart",2114,"Smart","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,15000, Transmission.Automatic.toString(), BodyStyle.Hatchback.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),504,"Smart",24758,"Eq fortwo","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,6000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),471,"Opel",1840,"Ampera","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2021,15000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),471,"Opel",4784,"Roadster","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Hatchback.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),441,"Tesla",17834,"Model 3","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2019,20000, Transmission.Automatic.toString(), BodyStyle.Pickup.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),441,"Tesla",10199,"Model x","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2022,7000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),492,"Fiat",2037,"500l","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2018,50000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),452,"Bmw",9570,"M4","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,40000, Transmission.Automatic.toString(), BodyStyle.Minivan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),452,"Bmw",1719,"X3","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),452,"Bmw",1716,"535i","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2019,50000, Transmission.Automatic.toString(), BodyStyle.Cabriolet.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),482,"Volkswagen",3133,"Golf","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2018,70000, Transmission.Automatic.toString(), BodyStyle.Minivan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),482,"Volkswagen",3136,"Touareg","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),482,"Volkswagen",3137,"Jetta","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),466,"Lotus",3106,"Elise","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),476,"Dodge",1895,"Charger","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),476,"Dodge",1893,"Challenger","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),498,"Hyundai",2058, "Tucson","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        car=new Car(UUID.randomUUID().toString(),498,"Hyundai",2735,"Elantra","/apps/vehicle/components/header/clientlib/resources/carLogo.png",2020,30000, Transmission.Automatic.toString(), BodyStyle.Sedan.toString());
        cars.add(car);
        return cars;
    }

}
