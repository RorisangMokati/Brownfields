package za.co.wethinkcode.robotworlds.Server.World.IWorld;


import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.World.Position;

import java.util.List;

public abstract class AbstractWorld implements IWorld{

    public static Position TOP_LEFT = new Position(-7,7);
    public static Position BOTTOM_RIGHT= new Position(7,-7);
    public static int visibility;
    public static int reload = 5 ;
    public static int repair = 5;


    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world.
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    public boolean isNewPositionAllowed(Position position) {
        return (position.isIn(TOP_LEFT,BOTTOM_RIGHT));
    }

    /**
     * Checks if the robot is at one of the edges of the world
     * @return true if the robot's current is on one of the 4 edges of the world
     */
    public boolean isAtEdge(Position pos) {
        return (pos.getX() == TOP_LEFT.getX() ||
                pos.getX() == BOTTOM_RIGHT.getX() ||
                pos.getY() == TOP_LEFT.getY() ||
                pos.getY() == BOTTOM_RIGHT.getY());
    }

    public AbstractWorld getWorld(){
        return this;
    }

    public abstract List<JSONObject> getObjects(Robot robot);

}