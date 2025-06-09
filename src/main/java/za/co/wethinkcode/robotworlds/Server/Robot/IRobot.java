package za.co.wethinkcode.robotworlds.Server.Robot;

import za.co.wethinkcode.robotworlds.Server.World.Position;
import za.co.wethinkcode.robotworlds.Server.World.IWorld.AbstractWorld;

/**
 * Interface to use for robot classes.
 */
public interface IRobot {

    String getName();

    String getResult();

    void setResult(String result);

    /**
     * Retrieves the current position of the robot
     */
    Position getPosition();

    void setMessage(String message);

    String getMessage();

    AbstractWorld getWorld();
}

