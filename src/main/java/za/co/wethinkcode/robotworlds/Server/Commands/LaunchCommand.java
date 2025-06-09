package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Response;
import za.co.wethinkcode.robotworlds.Server.World.World;

import java.util.List;

public class LaunchCommand extends Command {
    // public Robot target;

    public LaunchCommand(List<String> arg, RobotCreator robot, String robotName, String robotMake) {
        super("launch", arg);

        if (World.robotNames.contains(robotName)) {
            response = Response.invalidRobotNameResponse(new Robot(robotName, robotMake, robot.world()));
        } else if (robot == null) {
            response = Response.invalidLaunchResponse();
        } else {
            robot.setRobot(new Robot(robotName, robotMake, robot.world()));
            World.robotNames.add(robotName);
            execute(robot);
        }
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        response = Response.createLaunchResponse(target.getRobot());
        System.out.println(response);
        return response;
    }
}
