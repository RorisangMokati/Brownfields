package AcceptanceTest2x2Without;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldClient;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldJsonClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    void seeRobotsandObstacle() throws IOException, InterruptedException {
        // Given a world of size 2x2
        //and the world has an obstacle at coordinate [0,1]

        List<RobotWorldClient> clientList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            //more than one robot has been launched into the world
            JSONObject request = new JSONObject();
            request.put("robot", String.format("HAL%d", i));
            request.put("command", "launch");
            request.put("arguments", new Object[]{"shooter", 5, 5});

            RobotWorldClient client = new RobotWorldJsonClient();
            clientList.add(client);
            client.connect(DEFAULT_IP, DEFAULT_PORT);
            client.sendRequest(request.toString());
        }

        // When I ask the robot to "look" around
        String request2 = "{" +
                "  \"robot\": \"HAL0\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);

        // Then I should get a response from the server with the result okay
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // Then I should get a response back with
        // one object being an OBSTACLE that is one step away
        // and three objects should be ROBOTs that is one step away
        String obsType = "OBSTACLE";
        String roboType = "ROBOT";
        String edgeType = "EDGE";
        String expectedDirection = "NORTH";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.traverse());

        JsonNode dataNode = rootNode.get("data");
        JsonNode objectsNode = dataNode.get("objects");

        //And I should get a response back with one object being an OBSTACLE that is one step away[DIRECTION]
        //And a ROBOT that is one step away[DIRECTION].

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
            if (roboType.equals(type)) {
                assertEquals(roboType, type, String.valueOf(true));
                assertNotNull(direction);
                assertEquals(1, distance, String.valueOf(true));
            }

        }
        for (RobotWorldClient client : clientList) {
            Thread.sleep(50);
            client.disconnect();
        }
    }
}
