package AcceptanceTest2x2Without;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldClient;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * As a player
 * I want to command my robot to move forward a specified number of steps
 * so that I can explore the world and not be a sitting duck in a battle.
 */
public class MoveForwardTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP,DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }

    @Test
    void AtTheEdge(){
        // And a robot called "HAL" is already connected and launched
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);
//        JsonNode response1 = serverClient.sendRequest(request);

        // When I send a command for "HAL" to move forward by 5 steps
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";
        serverClient.sendRequest(request2);

        // Then I should get an "OK" response with the message "At the NORTH edge"
        JsonNode response = serverClient.sendRequest(request2);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertEquals("At the NORTH edge", response.get("data").get("message").asText());

        // and the position information returned should be at co-ordinates [0,0]
        assertNotNull(response.get("state"));
        assertEquals("[0,0]", response.get("state").get("position").toString());

    }

}
