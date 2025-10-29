import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.Pet;
import org.junit.Test;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestServiceClient {


    @Test
    public void testPetStoreJSON() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://petstore.swagger.io/v2/pet/1");
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

        ObjectMapper mapper = new ObjectMapper();
        Pet pet = mapper.readValue(response, Pet.class);
        assertEquals(1, pet.getId());
    }
}
