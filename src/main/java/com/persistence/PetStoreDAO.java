package com.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.Pet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

public class PetStoreDAO implements PropertiesLoader {

    private static final Logger logger = LogManager.getLogger(PetStoreDAO.class);

    public Pet getPet() throws Exception {
        // Load URL from properties file
        Properties properties = loadProperties("/petstore.properties"); // path in resources folder
        String url = properties.getProperty("petstore.url");

        if (url == null || url.isEmpty()) {
            logger.error("PetStore URL is missing in properties file.");
            throw new RuntimeException("PetStore URL not found in properties file.");
        }

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);

        String response;
        try {
            response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        } catch (Exception e) {
            logger.error("Failed to fetch pet from PetStore API", e);
            throw new RuntimeException("API request failed", e);
        }

        ObjectMapper mapper = new ObjectMapper();
        Pet pet;
        try {
            pet = mapper.readValue(response, Pet.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON response into Pet object", e);
            throw new RuntimeException(e);
        }

        logger.info("Successfully fetched pet with ID {}", pet.getId());
        return pet;
    }
}