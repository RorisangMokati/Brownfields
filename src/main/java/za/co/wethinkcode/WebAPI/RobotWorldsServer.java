package za.co.wethinkcode.WebAPI;

import io.javalin.Javalin;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.World.Response;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.WorldConfig;

public class RobotWorldsServer {
    private final Javalin server;
    private static WorldConfig worldConfig = new WorldConfig();
    private static World world = new World(worldConfig.extractDataFromConfigFile());

    public RobotWorldsServer(String response) {
        server = Javalin.create();
        server.get("/world", context -> {
            // Create a JSON response and set the content type
            context.contentType("application/json");
            RobotWorldsAPI.getWorld(context);
        });
        server.post("/robot", context -> {
            // Create a JSON response and set the content type
            context.contentType("application/json");
            RobotWorldsAPI.getRobotLaunchResponse(context);
        });
    }

    public static void main(String[] args) {
        RobotWorldsServer server = new RobotWorldsServer(Response.createLaunchResponse(new Robot("null", "sniper", world)).toString());
        server.start(5000);
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
}
