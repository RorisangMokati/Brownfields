package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;

import java.util.ArrayList;
import java.util.List;

//
public abstract class Command {
    private final String name;
    public JSONObject response;
    private List<String> arg;

    public abstract JSONObject execute(RobotCreator target);


    public Command(String name) {
        this(name, new ArrayList<>());
    }

    public Command(String name, List<String> arg) {
        this.name = name.trim().toLowerCase();
        this.arg = arg;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getArg() {
        return this.arg;
    }

    public static Command create(String command, List<String> arg, RobotCreator robot, String robotName, String robotMake) {
        switch (command.toLowerCase()) {
            case "launch":
                return new LaunchCommand(arg, robot, robotName, robotMake);
            case "state":
                return new StateCommand(robot);
            case "look":
                return new LookCommand(robot);
            case "forward":
                return new ForwardCommand(arg, robot);
            case "back":
                return new BackCommand(arg, robot);
            case "turn":
                if(arg.contains("left")){
                    return new LeftCommand(robot);
                }
                else {
                    return new RightCommand(robot);
                }
            case "repair":
                return new RepairCommand(robot);
            case "reload":
                return new ReloadCommand(robot);
            case "fire":
                return new FireCommand(robot);
            case "quit":
                return new QuitCommand(robot, robotName);
            default:
                return new ErrorCommand(robot);
        }
    }
}
