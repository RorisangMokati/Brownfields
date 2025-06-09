package AcceptanceTest2x2Obstacle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldClient;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldJsonClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LookAroundTest {
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
    void seeObstacle() throws IOException {
        // Given a world of size 2x2
        //and the world has an obstacle at coordinate [0,1]
        //and I have successfully launched a robot into the world
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);

        // When I ask the robot to look
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);

        // Then I should get a response from the server with the result okay
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        //And I should get a response back with an object of type OBSTACLE at a distance of one step[DIRECTION]

        String obsType = "OBSTACLE";
        String edgeType = "EDGE";
        String expectedDirection = "NORTH";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.traverse());

        JsonNode dataNode = rootNode.get("data");
        JsonNode objectsNode = dataNode.get("objects");
        assertEquals(5, objectsNode.size());

        for (JsonNode objectNode : objectsNode) {
            String type = objectNode.get("type").asText();
            String direction = objectNode.get("direction").asText();
            int distance = objectNode.get("distance").asInt();

            if (obsType.equals(type) && expectedDirection.equals(direction) && distance == 1) {
                assertEquals(obsType, type, String.valueOf(true));
                assertEquals(expectedDirection, direction, String.valueOf(true));
                assertEquals(1, distance, String.valueOf(true));
                break;
            }
            if (edgeType.equals(type)) {
                assertEquals(edgeType, type, String.valueOf(true));
                assertNotNull(direction);
                assertEquals(1, distance, String.valueOf(true));
            }

        }

    }
}
