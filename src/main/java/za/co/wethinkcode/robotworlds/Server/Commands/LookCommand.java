package za.co.wethinkcode.robotworlds.Server.Commands;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.Obstacles;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;

public class LookCommand extends Command {
    public Robot target;
    public LookCommand(RobotCreator robot) {
        super("look");
        execute(robot);
    }

    @Override
    public JSONObject execute(RobotCreator target) {
        String result = "OK";
        JSONObject response = new JSONObject();

        // World world = target.getRobot().getWorld();
        // List<JSONObject> objects = world.getObjects(target.getRobot());
        response.put("result",result);
        JSONObject data = new JSONObject();
        data.put("objects", Obstacles.getHardCodedObstacles());
        response.put("data", data);
//        World world = new World(); // this shouldn't be a new world, it should be the current world?
//        objects = target.world().getObjects(target.getRobot()); // TODO: need list of objects in world
//        response = Response.createLookResponse(target.getRobot(), result, objects);
        System.out.println(response);
        this.response = response;
        return response;
    }
}
