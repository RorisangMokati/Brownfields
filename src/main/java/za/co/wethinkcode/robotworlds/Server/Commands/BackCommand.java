package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Direction;
import za.co.wethinkcode.robotworlds.Server.World.IWorld.IWorld;
import za.co.wethinkcode.robotworlds.Server.World.Direction;
import za.co.wethinkcode.robotworlds.Server.World.Response;

import java.util.List;

public class BackCommand extends Command {
    public Robot target;

    public BackCommand(List<String> arg, RobotCreator robot) {
        super("back", arg);
        execute(robot);
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        List<String> args = getArg();
        String message = "";
        String result;
        JSONObject response;
        int distance = Integer.parseInt(args.get(0));
        IWorld.UpdateResponse updateResult = target.getRobot().updatePosition(-distance);

        if (updateResult == IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD) {
            message = getEdgeMessage(target.getRobot().getCurrentDirection());
        }

        result = "OK";
        response = Response.createBackResponse(target.getRobot(), result, message);
        System.out.println(response);
        this.response = response;
        return response;
    }

    private String getEdgeMessage(Direction direction) {
        if (direction == Direction.WEST) {
            return "At East Edge";
        } else if (direction == Direction.SOUTH) {
            return "At North Edge";
        } else if (direction == Direction.EAST) {
            return "At West Edge";
        } else if (direction == Direction.NORTH) {
            return "At South Edge";
        } else {
            return "At an Edge";
        }
    }
}
