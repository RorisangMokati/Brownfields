package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;

import static za.co.wethinkcode.robotworlds.Server.World.Response.createErrorResponse;

public class ErrorCommand extends Command{

    public ErrorCommand(RobotCreator robot){
        super("Error");
        execute(robot);
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        this.response = createErrorResponse("unsupported command");
        return this.response;
    }
}
