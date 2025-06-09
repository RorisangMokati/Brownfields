package za.co.wethinkcode.robotworlds.Server.World;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;

public class Response {

    public static JSONObject createErrorResponse(String errorType) {
        String message;
        String result = "ERROR";
        if (errorType.equals("unsupported argument")) {
            message = "Could not parse arguments";
        } else if (errorType.equals("unsupported command")) {
            message = "Unsupported command";
        } else {
            message = "Unspecified error occurred";
        }

        JSONObject dataObject = new JSONObject();
        JSONObject errorResponseObject = new JSONObject();
        errorResponseObject.put("result", result);
        dataObject.put("message", message);
        errorResponseObject.put("data", dataObject);

        return errorResponseObject;
        // errorResponseObject ready to be sent to the client
    }

    public static JSONObject createLaunchErrorResponse(String errorType) {
        String message;
        String result = "ERROR";

        if (errorType.equals("No free location")) {
            message = "No more space in this world";
        } else if (errorType.equals("Name already taken")) {
            message = "Too many of you in this world";
        } else {
            message = "Unspecified error occurred. Launching failed";
        }

        JSONObject dataObject = new JSONObject();
        JSONObject launchErrorResponseObject = new JSONObject();
        launchErrorResponseObject.put("result", result);
        dataObject.put("message", message);
        launchErrorResponseObject.put("data", dataObject);

        return launchErrorResponseObject;
        // launchErrorResponseObject ready to be sent to client
    }

    public static JSONObject createLaunchResponse(Robot target) {
        JSONObject launchResponseObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject stateObject = createStateObject(target);
        String result = "OK";

        dataObject.put("position", List.of(target.getPosition().getX(), target.getPosition().getY()));
        dataObject.put("visibility", target.getVisibility());
        dataObject.put("reload", target.getReloadDelay());
        dataObject.put("repair", target.getRepairDelay());
        dataObject.put("shields", target.getCurrentShield());

        launchResponseObject.put("result", result);
        launchResponseObject.put("data", dataObject);
        launchResponseObject.put("state", stateObject);

        return launchResponseObject;
        // launchResponseObject ready to go back to client
    }

    public static JSONObject invalidRobotNameResponse(Robot target) {
        JSONObject launchResponseObject = new JSONObject();
        String result = "ERROR";
        JSONObject dataObject = new JSONObject();
        String data = "Too many of you in this world";
        dataObject.put("message", data);

        launchResponseObject.put("result", result);
        launchResponseObject.put("data", dataObject);

        return launchResponseObject;
    }

    public static JSONObject invalidLaunchResponse() {
        JSONObject launchResponseObject = new JSONObject();
        String result = "ERROR";
        JSONObject dataObject = new JSONObject();
        String data = "No more space left in this world";
        dataObject.put("message", data);

        launchResponseObject.put("result", result);
        launchResponseObject.put("data", dataObject);

        return launchResponseObject;
    }

    public static JSONObject createStateResponse(Robot target) {

        String result = "OK";
        JSONObject responseObject = new JSONObject();

        JSONObject stateObject = createStateObject(target);
        responseObject.put("result", result);
        responseObject.put("state", stateObject);
        return responseObject;
        // responseObject ready to be sent to client
    }

    public static JSONObject createStateObject(Robot target) {

        JSONObject stateObject = new JSONObject();
        stateObject.put("position", List.of(target.getPosition().getX(), target.getPosition().getY()));
        stateObject.put("direction", target.getCurrentDirection());
        stateObject.put("shields", target.getCurrentShield());
        stateObject.put("shots", target.getShots());
        stateObject.put("status", target.getStatus());

        return stateObject;
    }

    public static JSONObject createQuitResponse(String name) {
        JSONObject quitResponse = new JSONObject();
        String result = "OK";

        String data = name + " has left the world";
        JSONObject dataObject = new JSONObject();

        dataObject.put("message", data);
        quitResponse.put("result", result);
        quitResponse.put("data", dataObject);
        return quitResponse;
    }

    public static JSONObject createLookResponse(Robot target, String result, List<JSONObject> objects) {
        JSONObject response = new JSONObject();
        response.put("result", result);

        JSONObject dataObject = new JSONObject();
        dataObject.put("objects", new JSONArray(objects));
        response.put("data", dataObject);

        JSONObject stateObject = createStateObject(target);
        response.put("state", stateObject);

        return response;

    }

    public static JSONObject createForwardResponse(Robot target, String result, String message) {
        JSONObject dataObject = new JSONObject();
        JSONObject stateObject = createStateObject(target);
        JSONObject forwardResponseObject = new JSONObject();

        forwardResponseObject.put("result", result);
        dataObject.put("message", message);
        forwardResponseObject.put("data", dataObject);
        forwardResponseObject.put("state", stateObject);

        return forwardResponseObject;
        // send forwardResponseObject.toString() to client for parsing and printing
    }

    public static JSONObject createBackResponse(Robot target, String result, String message) {
        JSONObject dataObject = new JSONObject();
        JSONObject stateObject = createStateObject(target);
        JSONObject backResponseObject = new JSONObject();

        backResponseObject.put("result", result);
        dataObject.put("message", message);
        backResponseObject.put("data", dataObject);
        backResponseObject.put("state", stateObject);

        return backResponseObject;
        // backResponseObject ready to be sent to client
    }

    public static JSONObject createLeftResponse(Robot target, String result, String message) {
        JSONObject dataObject = new JSONObject();
        JSONObject stateObject = createStateObject(target);
        JSONObject leftResponseObject = new JSONObject();

        dataObject.put("message", message);
        leftResponseObject.put("result", result);
        leftResponseObject.put("data", dataObject);
        leftResponseObject.put("state", stateObject);

        return leftResponseObject;
        // leftResponseObject ready to be sent to client
    }

    public static JSONObject createRightResponse(Robot target, String result, String message) {
        JSONObject dataObject = new JSONObject();
        JSONObject stateObject = createStateObject(target);
        JSONObject rightResponseObject = new JSONObject();

        dataObject.put("message", message);
        rightResponseObject.put("result", result);
        rightResponseObject.put("data", dataObject);
        rightResponseObject.put("state", stateObject);

        return rightResponseObject;
        // rightResponseObject ready to be sent to client
    }
}