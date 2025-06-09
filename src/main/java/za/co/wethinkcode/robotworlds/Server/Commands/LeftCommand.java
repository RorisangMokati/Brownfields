package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Direction;
import za.co.wethinkcode.robotworlds.Server.World.Response;

public class LeftCommand extends Command {
    public Robot target;
    public LeftCommand(RobotCreator robot) {
        super("left");

        execute(robot);
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        String result = "OK";
        String message = "Done";
        JSONObject response;
        Direction newDirection = pickDirection(target.getRobot());

        target.getRobot().setCurrentDirection(newDirection);

        response = Response.createLeftResponse(target.getRobot(), result, message);
        System.out.println(response);
        this.response = response;
        return response;
    }

    private Direction pickDirection(Robot target) {
        Direction newDirection;
        if (Direction.NORTH.equals(target.getCurrentDirection())) {
            newDirection = Direction.WEST;
        } else if (Direction.EAST.equals(target.getCurrentDirection())) {
            newDirection = Direction.NORTH;
        } else if (Direction.SOUTH.equals(target.getCurrentDirection())) {
            newDirection = Direction.EAST;
        } else {
            newDirection = Direction.SOUTH;
        }
        return newDirection;
    }
}
