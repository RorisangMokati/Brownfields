package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;

public class ReloadCommand extends Command {
    public ReloadCommand(RobotCreator robot) {
        super("reload");
    }


    @Override
    public JSONObject execute(RobotCreator target) {
        return null;
    }
}
