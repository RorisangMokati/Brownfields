package za.co.wethinkcode.robotworlds.Client.jsonUtility;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonClient {

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
     * Converts a JsonNode to a Java object of the specified class.
     * 
     * @param <A> the type of the Java object
     * @param node the JsonNode to convert
     * @param clazz the class of the Java object
     * @return the converted Java object
     * @throws JsonProcessingException if a processing exception occurs
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(node, clazz);
    }

    /**
     * Converts a Java object to a JsonNode.
     * 
     * @param s the Java object to convert
     * @return the converted JsonNode
     */
    public static JsonNode toJson(Object s) {
        return objectMapper.valueToTree(s);
    }

    /**
     * Generates a compact JSON string from a JsonNode.
     * 
     * @param node the JsonNode to convert to a string
     * @return the compact JSON string
     * @throws JsonProcessingException if a processing exception occurs
     */
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateString(node, false);
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
