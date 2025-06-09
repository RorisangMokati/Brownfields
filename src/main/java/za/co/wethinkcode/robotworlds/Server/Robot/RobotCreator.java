package za.co.wethinkcode.robotworlds.Server.Robot;

import za.co.wethinkcode.robotworlds.Server.World.World;

public class RobotCreator {
    private Robot robot;
    String name;
    private final World robotWorld;

    /**
     * Constructs a new RobotCreator instance with the given parameters.
     *
     * @param world The World object where the robot will be created and managed.
     * @param robotName The name of the robot.
     * @param robotMake The make or model of the robot.
     *
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    public RobotCreator(World world, String robotName, String robotMake){
        this.robotWorld = world;
        this.name = robotName;
        this.robot = new Robot(robotName, robotMake, world);
    }

    public World world() {
        return this.robotWorld;
    }

    public Robot getRobot()
    {
        return this.robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
        this.world().addRobotNamesToWorld(this.name);
        this.robotWorld.setRobotsInWorld(robot);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
