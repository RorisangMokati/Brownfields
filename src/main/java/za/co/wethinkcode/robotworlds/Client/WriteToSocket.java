package za.co.wethinkcode.robotworlds.Client;

import java.util.List;
import java.util.ArrayList;

public class WriteToSocket {

    private String robot;
    private String command;
    private final List<String> arguments = new ArrayList<>();

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments.add(arguments);
    }

    public void clearArgument(){
        this.arguments.clear();
    }
}
