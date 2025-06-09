package AcceptanceTest2x2Without;

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

    private static  World world;

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void canLaunchAnotherRobot() {
        assertTrue(serverClient.isConnected());

        // Given a world of size 2x2 and robot "HAL" has already been launched
        launchRobot("HAL", 0, 0);

        // When I launch robot "R2D2" into the world
        JsonNode response = serverClient.sendRequest(buildLaunchRequest("R2D2", null, null));

        // Then the launch should be successful and a randomly allocated position should be returned
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
    }

    @Test
    void launchRobotsIntoWorldWithObstacle() {

        assertTrue(serverClient.isConnected());
        String request = "{" +
                "  \"robot\": \" Sandile\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        if(response.has("result")){
            String result =response.get("result").asText();
            if (result.equals("OK")){
                assertNotNull(response.get("data").get("position"));
                JsonNode position = response.get("data").get("position");
                assertFalse(position.get(0).asInt() == 1 && position.get(1).asInt() == 1);

            }

        // Given a world of size 2x2 with an obstacle at coordinate [1,1]
        placeObstacle(1, 1);

        }
    }

    @Test
    void worldWithoutObstaclesIsFull() {

        assertTrue(serverClient.isConnected());

        // Given a world of size 2x2 and I have successfully launched 9 robots into the world
        for(int i = 0;i < 9;i++){
            String request = "{" +
                    "  \"robot\": \"HAL"+i+"\"," +
                    "  \"command\": \"launch\"," +
                    "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";
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

        // Then I should get an error response with the message "No more space in this world"
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("No more space in this world"));

    }

    // Utility method to launch a robot with specified name and optional position
    private void launchRobot(String robotName, Integer x, Integer y) {
        JsonNode response = serverClient.sendRequest(buildLaunchRequest(robotName, x, y));
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
    }

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

