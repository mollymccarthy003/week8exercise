package com.persistence;

import com.petstore.Pet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetStoreDAOTest {

    @Test
    public void testGetPet() throws Exception {
        // Create DAO instance
        PetStoreDAO dao = new PetStoreDAO();

        // Fetch pet from API
        Pet pet = dao.getPet();

        // Assert key fields
        assertEquals(1, pet.getId(), "Pet ID should be 1");
        assertEquals("doggie", pet.getName(), "Pet name should match API");
        assertEquals("available", pet.getStatus(), "Pet status should match API");
    }
}
