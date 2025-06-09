package AcceptanceTest1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldClient;
import za.co.wethinkcode.robotworlds.ReferenceServer.RobotWorldJsonClient;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.WorldConfig;

import static org.junit.jupiter.api.Assertions.*;

public class StateCommandAcceptanceTest {
    private final WorldConfig worldConfig = new WorldConfig();
    private final World world = new World(worldConfig.extractDataFromConfigFile());
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
    void invalidRobot() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // Then I should get an error response
//        when the Robot is not launched
//        when I send a state request
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

//      The response should contain "result": "result of the command",
        //  "data": {
        //    "key1": value1,
        //    "key2": value2,
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Robot does not exist"));
    }

    @Test
    void validRobotState() {


        assertTrue(serverClient.isConnected());

        // When I have successfully launched the robot with the name HAL
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);

        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);
        System.out.println(response);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertNotNull(response.get("state"));
        assertEquals("[0,0]", response.get("state").get("position").toString());

        for (int i = 0; i < 2; i++) {
            assertEquals(0, response.get("state").get("position").get(i).asInt());
        }

        assertEquals("NORTH", response.get("state").get("direction").asText());


    }

}