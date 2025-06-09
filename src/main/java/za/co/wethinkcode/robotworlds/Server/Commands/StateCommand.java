package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Response;

/**
 * This class handles the "state" command which retrieves the current state of a robot.
 */
public class StateCommand extends Command {
    public Robot target;

    /**
     * Constructs a new StateCommand with the specified robot.
     *
     * @param robot the robot for which to retrieve the state.
     */
    public StateCommand(RobotCreator robot) {
        super("state");
        // Robot target = new Robot();
        execute(robot);
    }

    /**
     * Executes the command to retrieve the state of the specified robot.
     *
     * @param target the robot for which to retrieve the state.
     * @return the state of the robot in JSON format.
     */
    @Override
    public JSONObject execute(RobotCreator target) {
        if(target == null || target.getRobot() == null){
            String result = "ERROR";
            String message = "Robot does not exist";
            JSONObject dataObject = new JSONObject();
            dataObject.put("message", message);
            JSONObject response = new JSONObject();
            response.put("result", result);
            response.put("data", dataObject);
            this.response = response;
            return response;
        }
        JSONObject response = Response.createStateResponse(target.getRobot());
        System.out.println(response);
        this.response = response;
        return response;
    }

}
