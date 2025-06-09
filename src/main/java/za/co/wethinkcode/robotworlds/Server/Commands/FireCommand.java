package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;

public class FireCommand extends Command {
    public FireCommand(RobotCreator robot) {
        super("fire");
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        return null;
    }
}
