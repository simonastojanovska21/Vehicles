package com.vehicle.core.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.core.services.HttpClientService;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component(service = HttpClientService.class)
public class HttpClientServiceImpl implements HttpClientService{


    @Override
    public String getCarModelsForBrandInJSON(String brand) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        String URL = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/"+brand+"?format=json";
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readTree(response.body()).get("Results").toString();
    }
}
