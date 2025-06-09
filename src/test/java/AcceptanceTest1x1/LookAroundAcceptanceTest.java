package AcceptanceTest1x1;

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
 * I want my robot to look around in the online robot world
 * So that I can find robots to kill
 */

class LookAroundAcceptanceTest {

    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }


    @Test
    void emptyWorld()  {
        // When I send a valid launch request to the server
        // When I ask the robot to look
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a response from the server with the result okay
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        //Then I should get a response back with
        //object being an EDGE that is zero step away


    }

    @Test
    void noRobotLaunched() {
        // When I send a look request to the server in an empty world
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response from the server
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Robot does not exist"
        assertNotNull(response.get("data"));
        assertEquals("Robot does not exist", response.get("data").get("message").asText());
    }

}