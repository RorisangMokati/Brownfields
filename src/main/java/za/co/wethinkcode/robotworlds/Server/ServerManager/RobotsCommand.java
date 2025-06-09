package za.co.wethinkcode.robotworlds.Server.ServerManager;

import java.util.List;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.World.World;

public class RobotsCommand extends ServerCommands {
    private final World world;
    public RobotsCommand(World world) {
        this.world = world;
        execute();
    }

    //Dumps the list of robots that are active in the world
    public void execute() {

        List<Robot> robotList = this.world.getRobotsInWorld();
        StringBuilder output = new StringBuilder("List of robots.. \n");

        if (robotList.isEmpty()) {
            System.out.println("No robots connected!");
        } else {
            for (Robot robot : robotList) {
                if (!robot.getName().isEmpty()) {
                    output.append("Client name: ").append(robot.getName()).append("\n");
                }
            }
            System.out.println(output);
        }
    }
}
