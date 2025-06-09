package WebApi;

import io.javalin.Javalin;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.World.World;

public class WebApiServer {
    public static void main(String[] args) {
        // Instantiate the World object
        World world = new World();

        Javalin server = Javalin.create(config -> {
            // Configure Javalin here if needed
        }).start(7000);

        server.get("/hello", ctx -> {
            ctx.contentType("text/plain"); // Explicitly set content type
            ctx.result("Hello, world!");
        });

        // Endpoint to launch a robot
        server.post("/launch", ctx -> {
            String robotName = ctx.queryParam("name"); // Get robot name from query parameter
            if (robotName != null && !robotName.isEmpty()) {
                world.launchRobot(robotName); // Launch robot into the world
                world.addRobotNamesToWorld(robotName); // Add robot name to world
                ctx.result("Robot " + robotName + " launched into the world!");
            } else {
                ctx.status(400).result("Robot name is required");
            }
        });

        // Logging incoming requests
        server.before(ctx -> {
            System.out.println("Received request: " + ctx.method() + " " + ctx.url());
        });

        // Logging outgoing responses
        server.after(ctx -> {
            System.out.println("Responded with status: " + ctx.status());
            System.out.println("Response body: " + ctx.result());
        });
    }
}

//        // Logging outgoing responses
//        server.after(ctx -> {
//            System.out.println("Responded with status: " + ctx.status());
//            System.out.println("Response body: " + ctx.result());
//        });
//    }




