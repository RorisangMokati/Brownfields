package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Direction;
import za.co.wethinkcode.robotworlds.Server.World.IWorld.IWorld;
import za.co.wethinkcode.robotworlds.Server.World.Response;

import java.util.List;

public class ForwardCommand extends Command {
    // public Robot target;

    public ForwardCommand(List<String> arg, RobotCreator robot) {
        super("forward", arg);
        execute(robot);
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        List<String> args = getArg();

        String message;
        String result;
        JSONObject response;
        int distance = Integer.parseInt(args.get(0));
        IWorld.UpdateResponse updateResult = target.getRobot().updatePosition(distance);

        if (updateResult == IWorld.UpdateResponse.SUCCESS) {
            message = "Done";
        } else if (updateResult == IWorld.UpdateResponse.FAILED_OBSTRUCTED) {
            message = "Obstructed";
        } else if (updateResult == IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD) {
            message = "At the " + target.getRobot().getCurrentDirection() + " edge";
        } else message = "unexpected error occurred";

        if (message.equals("Done") || message.equals("At the " + target.getRobot().getCurrentDirection() + " edge")){
            result = "OK";
        } else {
            result = "ERROR";
        }
        response = Response.createForwardResponse(target.getRobot(), result, message);
        System.out.println(response);
        this.response = response;
        return response;
    }
}
