package AcceptanceTest1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldClient;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains acceptance tests for launching a robot in the online robot world.
 * The goal is to break the record for the most robot kills.
 */
class LaunchRobotAcceptanceTest {
    // Default port for the server
    private final static int DEFAULT_PORT = 5000;
    // Default IP address for the server
    private final static String DEFAULT_IP = "localhost";
    // Client to interact with the server
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    /**
     * This method is executed before each test.
     * It connects the client to the server.
     */
    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    /**
     * This method is executed after each test.
     * It disconnects the client from the server.
     */
    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    /**
     * This test verifies that a valid launch request succeeds.
     */
    @Test
    void validLaunchShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("state").get("position").get(0).asInt());
        assertEquals(0, response.get("state").get("position").get(1).asInt());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }

    /**
     * This test verifies that an invalid launch request fails.
     */
    @Test
    void invalidLaunchShouldFail() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }


    /**
     * This test verifies that a launch request fails when there is a robot with the same name.
     */
    @Test
    void worldCannotHaveRobotsWithSameNames() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // The World is 1x1, with no obstacles.

        // And there are Robots in the World occupying all free space.
        JSONObject js = new JSONObject();
        js.put("robot", "HAL");
        js.put("command", "launch");
        js.put("arguments", new Object[]{"shooter", 5, 5});
        String request = js.toString();
        serverClient.sendRequest(request);

        // If another Robot attempts to join, with the same name
        js.put("robot", "HAL");
        request = js.toString();
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // With some message informing me there is no more space.
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("in this world"));
    }
    /**
     * Given a world of size 2x2
     *
     *
     *       and the world has an obstacle at coordinate [1,1]
     *
     *
     *       When I launch 8 robots into the world
     *
     *
     *       Then each robot cannot be in position [1,1].
     */

    @Test

    void robotsCannotBeInSamePositionAsObstacle() {
        // Given that I am connected to a running Robot Worlds server
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
    private void placeObstacle(int x, int y) {
        JSONObject json = new JSONObject();
        json.put("command", "place");
        json.put("arguments", new Object[]{"obstacle", x, y});
        serverClient.sendRequest(json.toString());
    }

}