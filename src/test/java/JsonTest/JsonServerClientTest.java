package JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import JsonTest.pojo.SimpleTestCaseJsonPOJO;
import za.co.wethinkcode.robotworlds.Client.jsonUtility.JsonClient;
import za.co.wethinkcode.robotworlds.Server.jsonUtility.JsonServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonServerClientTest {

    private String simpleTestCaseJsonSource = "{ \"name\": \"HAL\", \"command\": \"launch\"}";


    @Test
    void parse() throws IOException {

        JsonNode node = JsonServer.parse(simpleTestCaseJsonSource);

        assertEquals(node.get("name").asText(), "HAL");
    }
    @Test
    void fromJson() throws IOException {
        JsonNode node = JsonServer.parse(simpleTestCaseJsonSource);

        SimpleTestCaseJsonPOJO pojo = JsonClient.fromJson(node, SimpleTestCaseJsonPOJO.class);

        assertEquals(pojo.getCommand(), "launch");
    }
    @Test
    void toJson(){
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setCommand("look");

        JsonNode node = JsonClient.toJson(pojo);

        assertEquals(node.get("command").asText(), "look");

    }
    @Test
    void stringify() throws JsonProcessingException {
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setCommand("look");

        JsonNode node = JsonClient.toJson(pojo);

        System.out.println(JsonClient.stringify(node));
        System.out.println(JsonClient.prettyPrint(node));

    }
}