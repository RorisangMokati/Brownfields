package za.co.wethinkcode.robotworlds.Server.jsonUtility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class JsonServer {

    // Static ObjectMapper instance initialized with default settings
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    /**
     * Initializes the default ObjectMapper with specific configurations.
     *
     * @return a configured ObjectMapper instance
     */
    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        // Configure ObjectMapper to ignore unknown properties during deserialization
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    /**
     * Parses a JSON string and returns the corresponding JsonNode.
     *
     * @param src the JSON string to parse
     * @return the parsed JsonNode
     * @throws IOException if an input/output exception occurs
     */
    public static JsonNode parse(String src) throws IOException {
        return objectMapper.readTree(src);
    }

    /**
     * Generates a pretty-printed JSON string from a JsonNode.
     *
     * @param node the JsonNode to convert to a pretty-printed string
     * @return the pretty-printed JSON string
     * @throws JsonProcessingException if a processing exception occurs
     */
    public static String prettyPrint(JsonNode node) throws JsonProcessingException {
        return generateString(node, true);
    }

    /**
     * Helper method to generate a JSON string from a JsonNode.
     *
     * @param node the JsonNode to convert
     * @param pretty whether to generate a pretty-printed string
     * @return the generated JSON string
     * @throws JsonProcessingException if a processing exception occurs
     */
    private static String generateString(JsonNode node, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        if (pretty) {
            // Configure ObjectWriter for pretty-printing
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(node);
    }
}