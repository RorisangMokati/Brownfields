package za.co.wethinkcode.robotworlds.Server.ServerManager;

import java.util.List;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.World.World;

public class DumpCommand extends ServerCommands{

    private final int height;
    private final int width;
    private final int visibility;
    private final World world;  //changed A

    public DumpCommand(World world) {
        this.height = world.getWorldHeight();
        this.width = world.getWorldWidth();
        visibility = world.getVisibility();
        this.world=world;
        dump();
    }

    /**
     * This function is responsible for dumping server details, including world dimensions, visibility,
     * connected clients, and obstacles. It prints these details to the console and returns a string
     * representation of the dumped information.
     *
     * @return A string representation of the dumped server details.
     */
    public String dump() {
        System.out.println("Server Details..\n");
        String dump = "";

        dump += "\nWorld height: "+ height;
        dump += "\nWorld width: "+ width;
        dump += "\nWorld area: "+ height * width;
        dump += "\nArea visible to robot: " + visibility;
        dump += "\n";

        List<Robot> robotList = this.world.getRobotsInWorld();
        StringBuilder output = new StringBuilder("Connected clients \n");

        if (robotList.isEmpty()) {
            System.out.println("None");
        } else {
            for (Robot robot : robotList) {
                if (!robot.getName().isEmpty()) {
                    output.append("Client name: ").append(robot.getName()).append("\n");
                }
            }
            System.out.println(output);
        }

        dump+="\n Obstacles: \n"+world.getObstaclesInWorld();//changed A
        System.out.println(dump);

        return dump;
    }
}
