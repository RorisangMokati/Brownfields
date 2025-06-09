package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Direction;
import za.co.wethinkcode.robotworlds.Server.World.Response;

public class RightCommand extends Command {
    public Robot target;
    public RightCommand(RobotCreator robot) {
        super("right");
        // Robot target = new Robot();
        execute(robot);
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        JSONObject response;
        String result = "OK";
        String message = "Done";
        Direction newDirection = pickDirection(target.getRobot());

        target.getRobot().setCurrentDirection(newDirection);

        response = Response.createRightResponse(target.getRobot(), result, message);
        System.out.println(response);
        this.response = response;
        return response;
    }

    private Direction pickDirection(Robot target) {
        Direction newDirection;
        if (Direction.NORTH.equals(target.getCurrentDirection())) {
            newDirection = Direction.EAST;
        } else if (Direction.EAST.equals(target.getCurrentDirection())) {
            newDirection = Direction.SOUTH;
        } else if (Direction.SOUTH.equals(target.getCurrentDirection())) {
            newDirection = Direction.WEST;
        } else {
            newDirection = Direction.NORTH;
        }
        return newDirection;
    }
}
