package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;

public class RepairCommand extends Command {
    public Robot target;
    public RepairCommand(RobotCreator robot) {
        super("repair");
    }


    @Override
    public JSONObject execute(RobotCreator target) {
        return null;
    }
}
