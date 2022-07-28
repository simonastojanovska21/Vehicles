package com.vehicle.core.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.models.dto.BrandCarModel;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Implementation of service logic for creating the repository structure.
 * It implements 1 method from the interface and uses private methods for creating the structure
 */
@Component(service = RepositoryStructureService.class)
public class RepositoryStructureServiceImpl implements RepositoryStructureService{

    private static final Logger log = LoggerFactory.getLogger(RepositoryStructureServiceImpl.class);

    @Reference
    ResourceResolverFactory resourceResolverFactory;

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
        }catch (RepositoryException | IOException | InterruptedException | LoginException e){
            log.error("Exception when creating the repository structure: {}",e.getMessage());
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
        ObjectMapper mapper = new ObjectMapper();
        List<String> brands = List.of("TESLA","MERCEDES-BENZ","BMW","OPEL","VOLKSWAGEN","JEEP","FIAT","HYUNDAI",
                "SMART","AUDI","BENTLEY","PORSCHE","LOTUS","BUGATTI","DODGE");
        /*
        Http client is created and request is sent to the api for getting the data for each brand from the list.
        From the json object returned from the API we are getting the Results object which is array of car models and
        brands. After that the json string is converted into a list of objects and nodes are created.
         */
        HttpClient client = HttpClient.newHttpClient();
        brands.forEach(brand->{
            try {
                String URL = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/"+brand+"?format=json";
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String jsonResults = mapper.readTree(response.body()).get("Results").toString();
                addNodesToRepository(session, jsonResults, brand);
            }catch (Exception e){
                log.error("Exception when calling the api: {}",e.getMessage());
            }
        });
//        for (String brand : brands) {
//            String URL = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/"+brand+"?format=json";
//            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            String jsonResults = mapper.readTree(response.body()).get("Results").toString();
//            addNodesToRepository(session, jsonResults);
//
//        }

    }

    /**
     * Helper method of importDataFromApi used for adding the nodes in the repository as specified earlier
     * @param session
     * @param jsonResults
     */
    private void addNodesToRepository(Session session, String jsonResults, String brand) throws RepositoryException, IOException {
        //Getting the root location for adding the brands and car models.
        Node brandsNodeRootNode = session.getNode(Constants.BRANDS_NODE_LOCATION);
        Node carModelsRootNode =  session.getNode(Constants.CAR_MODELS_NODE_LOCATION);
        ObjectMapper mapper = new ObjectMapper();
        //Mapping the json array to List of BrandCarModel object
        List <BrandCarModel> brandCarModels = mapper.readValue(jsonResults, new TypeReference<List<BrandCarModel>>(){});
        //For each BrandCarModel object we check if there is node for the brand. If not, node for brand data is created
        //Finally node for car model data is created
        brandCarModels.forEach(each->{
            if(each.getBrandName().equalsIgnoreCase(brand)){
                try {
                    if(!brandsNodeRootNode.hasNode("Brand"+each.getBrandId())){
                        Node brandNode = brandsNodeRootNode.addNode("Brand"+each.getBrandId());
                        brandNode.setProperty("BrandId",each.getBrandId());
                        brandNode.setProperty("BrandName",each.getBrandName());
                    }
                    if(!carModelsRootNode.hasNode("CarModelId"+each.getCarModelId())){
                        Node carModel = carModelsRootNode.addNode("CarModel"+each.getCarModelId());
                        carModel.setProperty("CarModelId",each.getCarModelId());
                        carModel.setProperty("CarModelName",each.getCarModelName());
                        carModel.setProperty("BrandId",each.getBrandId());
                    }
                } catch (RepositoryException e) {
                    log.error("Exception when adding the nodes in the repository: {}",e.getMessage());
                }
            }
        });
        log.info("Data saved in repository");
    }
}
