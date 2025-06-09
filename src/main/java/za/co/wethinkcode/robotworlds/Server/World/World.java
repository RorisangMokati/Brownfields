package za.co.wethinkcode.robotworlds.Server.World;

import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.RobotServer.CommandLineArguments;
import za.co.wethinkcode.robotworlds.Server.World.IWorld.AbstractWorld;

import java.util.ArrayList;
import java.util.List;

public class World extends AbstractWorld {
    private List<Obstacle> obstaclesInWorld;
    private static List<Robot> robotsInWorld = new ArrayList<>();
    public static int worldCapacity = (int) Math.pow((2 * CommandLineArguments.getSize() - 1), 2);

    private Position TOP_LEFT;
    private Position BOTTOM_RIGHT;

    // needs to generate random square obstacles
    // needs to know what robots are in it
    // add to this list in launch command
    // remove from this list in client quit command
    private  int worldWidth;
    private  int worldHeight;
    private  int visibility;
    private int shieldStrength;
    private int shieldRepairDelay;
    private int weaponReloadDelay;
    public List<Robot> getRobotsInWorld;
    public static List<String> robotNames = new ArrayList<>();
    private JSONObject newWorld;

    public World(JSONObject data) {

        newWorld = (JSONObject)data.get("New World");
        System.out.println(newWorld);
        if (newWorld!=null){
            this.worldWidth = Integer.parseInt(String.valueOf(newWorld.getInt("X Barriers")));
            this.worldHeight = Integer.parseInt(String.valueOf(newWorld.getInt("Y Barriers")));
//            JSONObject type=(JSONObject) String.valueOf(newWorld.getInt("Visibility");
            this.visibility = Integer.parseInt(String.valueOf(newWorld.getInt("Visibility")));
//            JSONObject shields=(JSONObject) String.valueOf(newWorld.getInt("Shields");
            this.shieldStrength = Integer.parseInt(String.valueOf(newWorld.getInt("Shields")));
            this.shieldRepairDelay = Integer.parseInt(String.valueOf(newWorld.getInt("Shields")));
            this.weaponReloadDelay = Integer.parseInt(String.valueOf(newWorld.getInt("Shields")));
            this.TOP_LEFT = new Position(-this.worldWidth/2, this.worldHeight/2 );
            this.BOTTOM_RIGHT = new Position(this.worldWidth/2, -this.worldHeight/2 );
            getRobotsInWorld = getRobotsInWorld();
        }
        else if (data != null) {
            this.worldWidth = Integer.parseInt(data.get("world_width").toString());
            this.worldHeight = Integer.parseInt(data.get("world_height").toString());
            this.visibility = Integer.parseInt(data.get("visibility").toString());
            this.shieldStrength = Integer.parseInt(data.get("shield_strength").toString());
            this.shieldRepairDelay = Integer.parseInt(data.get("repair_delay").toString());
            this.weaponReloadDelay = Integer.parseInt(data.get("reload_delay").toString());
            this.TOP_LEFT = new Position(-this.worldWidth/2, this.worldHeight/2 );
            this.BOTTOM_RIGHT = new Position(this.worldWidth/2, -this.worldHeight/2 );
        } else {
            System.out.println("World unable to be constructed.");
        }
    }
    public World(int worldWidth, int worldHeight, List<Obstacle> obstacles) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.obstaclesInWorld = obstacles;
        this.TOP_LEFT = new Position(-worldWidth / 2, worldHeight / 2);
        this.BOTTOM_RIGHT = new Position(worldWidth / 2, -worldHeight / 2);
    }

    public World() {

    }

    public void addRobotNamesToWorld(String robotName) {
        robotNames.add(robotName);
    }

    public List<Obstacle> getObstaclesInWorld() {
        return obstaclesInWorld;
    }

    public void setRobotsInWorld(List<Obstacle> obstacles) {
        this.obstaclesInWorld = obstacles;
    }

    public void setObstaclesInWorld(List<Obstacle> obstaclesInWorld) {
        this.obstaclesInWorld = obstaclesInWorld;
    }

    public List<Robot> getRobotsInWorld() {
        return robotsInWorld;
    }

    public static void removeRobotsInWorld(Robot robot) {
        for (int i = 0; i < robotsInWorld.size(); i++) {
            if (robotsInWorld.get(i).equals(robot)) {
                robotsInWorld.remove(i);
            }
        }
    }

    public void setRobotsInWorld(Robot newRobot) {
        robotsInWorld.add(newRobot);
    }

    public  int getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }


    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }


    public  int getWorldHeight() {
        return worldHeight;
    }


    public  int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
    public int getShieldStrength() {
        return shieldStrength;
    }

    public void setShieldStrength(int shieldStrength) {
        this.shieldStrength = shieldStrength;
    }

    public int getShieldRepairDelay() {
        return shieldRepairDelay;
    }

    public void setShieldRepairDelay(int shieldRepairDelay) {
        this.shieldRepairDelay = shieldRepairDelay;
    }

    public int getWeaponReloadDelay() {
        return weaponReloadDelay;
    }

    public void setWeaponReloadDelay(int weaponReloadDelay) {
        this.weaponReloadDelay = weaponReloadDelay;
    }

    @Override
    public List<JSONObject> getObjects(Robot robot) {
        List<JSONObject> objects = new ArrayList<>();
        scanDirection(objects, robot, "NORTH", 0, 1);
        scanDirection(objects, robot, "SOUTH", 0, -1);
        scanDirection(objects, robot, "EAST", 1, 0);
        scanDirection(objects, robot, "WEST", -1, 0);
        return objects;
    }

    private void scanDirection(List<JSONObject> objects, Robot robot, String direction, int dx, int dy) {
        Position robotPosition = robot.getPosition();
        for (int i = 1; i <= this.visibility; i++) {
            Position currentPos = new Position(robotPosition.getX() + i * dx, robotPosition.getY() + i * dy);
            String type = checkObjects(robot, currentPos);
            if (type != null) {
                JSONObject object = new JSONObject();
                object.put("direction", direction);
                object.put("type", type);
                object.put("distance", i);
                objects.add(object);
                if (type.equals("OBSTACLE") || type.equals("EDGE")) {
                    break;
                }
            }
        }
    }

    private String checkObjects(Robot robot, Position position) {
        for (Obstacle obs : this.obstaclesInWorld) {
            if (position.equals(obs.getObstacle())) {
                return "OBSTACLE";
            }
        }
        for (Robot eachRobot : robotsInWorld) {
            if (!eachRobot.equals(robot) && position.equals(eachRobot.getPosition())) {
                return "ROBOT";
            }
        }
        if (isAtEdge(position)) {
            return "EDGE";
        }
        return null;
    }

    public boolean isAtEdge(Position position) {
        return position.getX() <= TOP_LEFT.getX() || position.getX() >= BOTTOM_RIGHT.getX()
                || position.getY() >= TOP_LEFT.getY() || position.getY() <= BOTTOM_RIGHT.getY();
    }



    public String toString() {
        return newWorld.toString();
    }

    public String launchRobot(String robotName) {
        return robotName;
    }
}
