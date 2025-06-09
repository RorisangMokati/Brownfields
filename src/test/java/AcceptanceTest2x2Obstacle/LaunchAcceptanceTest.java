package AcceptanceTest2x2Obstacle;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldClient;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldJsonClient;
import za.co.wethinkcode.robotworlds.Server.World.World;

import static org.junit.jupiter.api.Assertions.*;

public class LaunchAcceptanceTest {
    // Default port for the server
    private final static int DEFAULT_PORT = 5000;
    // Default IP address for the server
    private final static String DEFAULT_IP = "localhost";
    // Client to interact with the server
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    private static World world;

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void worldWithObstacleIsFull() {

        assertTrue(serverClient.isConnected());
        placeObstacle(1, 1);


        for (int i = 0; i < 8; i++) {
            String request = "{" +
                    "  \"robot\": \"HAL" + i + "\"," +
                    "  \"command\": \"launch\"," +
                    "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";

//        // Given a world of size 2x2 with an obstacle at [1,1]
//        placeObstacle(1, 1);
//
//        // Launch 8 robots into the world, avoiding [1,1]
//
            JsonNode response = serverClient.sendRequest(buildLaunchRequest("Robot" + i, null, null));
            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());
        }

        // When I launch one more robot
        String request = "{" +
                "  \"robot\": \" Sandile\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response back with the message "No more space in this world"
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertEquals("No more space in this world", response.get("data").get("message").asText());
    }

    // Utility method to launch a robot with specified name and optional position

    // Utility method to build a launch request JSON string
    private String buildLaunchRequest(String robotName, Integer x, Integer y) {
        JSONObject json = new JSONObject();
        json.put("robot", robotName);
        json.put("command", "launch");
        if (x != null && y != null) {
            json.put("arguments", new Object[]{"shooter", x, y});
        } else {
            json.put("arguments", new Object[]{"shooter"});
        }
        return json.toString();
    }

    // Utility method to place an obstacle at a given position
    private void placeObstacle(int x, int y) {
        JSONObject json = new JSONObject();
        json.put("command", "place");
        json.put("arguments", new Object[]{"obstacle", x, y});
        serverClient.sendRequest(json.toString());
    }
}

