package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Response;
import za.co.wethinkcode.robotworlds.Server.World.World;

public class QuitCommand extends Command {
    private String name = "";

    public QuitCommand(RobotCreator target, String name) {
        super("quit");
        this.name = name;
        execute(target);
        removeRobot(target);
    }

    public void removeRobot(RobotCreator target) {
        for (int i = 0; i < World.robotNames.size(); i++) {
            if (World.robotNames.get(i).equals(this.name)) {
                World.robotNames.remove(i);
            }
        }
        World.removeRobotsInWorld(target.getRobot());
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        response = Response.createQuitResponse(this.name);
        System.out.println(response);
        return response;
    }

}
