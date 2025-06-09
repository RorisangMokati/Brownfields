package za.co.wethinkcode.robotworlds.Server.Robot;

import za.co.wethinkcode.robotworlds.Server.World.*;
import za.co.wethinkcode.robotworlds.Server.World.IWorld.IWorld;

import java.util.Random;

public class Robot {
    private String name;
    private String make;
    private int x = setBorderLimit(WorldConfig.getXBorders());
    private int y = setBorderLimit(WorldConfig.getYBorders());
    private final Position TOP_LEFT = new Position(-this.x, this.y);
    private final Position BOTTOM_RIGHT = new Position(this.x, -this.y);
    private Position position;
    private Direction currentDirection;
    private int shots;
    private RobotStatus status;
    private int visibility;
    private int reloadDelay;
    private int repairDelay;
    private int shields;
    public World world;

    public Robot(String name, String robotMake, World world) {
        this.world = world;
        this.name = name;
        this.make = robotMake;
        setSpecificRobotType(robotMake);
        robotCannotBeOnTopOfAnotherRobot();
        this.currentDirection = Direction.NORTH;
        this.visibility = world.getVisibility();
        this.reloadDelay = world.getWeaponReloadDelay();
        this.repairDelay = world.getShieldRepairDelay();
        this.status = RobotStatus.NORMAL;
    }

    public int setBorderLimit(int number) {
        if (number % 2 == 0) {
            return number / 2;
        }
        return (number - 1) / 2;
    }

    public void robotCannotBeOnTopOfAnotherRobot() {
        int x = 0;
        int y = 0;
        Position newPosition = new Position(x ,y);
        for (int i = 0; i < this.world.getRobotsInWorld.size(); i++) {
            if (newPosition.equals(this.world.getRobotsInWorld.get(i).getPosition())) {
                x = generateRandomX();
                y = generateRandomY();
                newPosition = new Position(x, y);
                i--;
            }
        }
        this.position = newPosition;
    }

    @Override
    public boolean equals(Object compared) {
        if (this == compared) {
            return true;
        }
        
        if (!(compared instanceof Robot)) {
            return false;
        }

        Robot compareRobot = (Robot) compared;

        if (this.name.equals(compareRobot.name)) {
            return true;
        }
        return false;
    }

    public void setSpecificRobotType(String robotMake) {
        if (this.make.contains("sniper")) {
            this.shots = 5;
            this.shields = 3;
        } else if (this.make.contains("killer")) {
            this.shots = 7;
            this.shields = 2;
        } else if (this.make.contains("defender")) {
            this.shots = 3;
            this.shields = 7;
        }
    }

    public int getCurrentShield() {
        return this.shields;
    }

    private int generateRandomX() {
        Random rand = new Random();
        return rand.nextInt((this.x - (-this.x) + 1) + (-this.x));
    }

    private int generateRandomY() {
        Random rand = new Random();
        return rand.nextInt((this.y - (-this.y) + 1) + (-this.y));
    }

    public Position getPosition() {
        return this.position;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction newDirection) {
        this.currentDirection = newDirection;
    }

    public int getShots() {
        return shots;
    }

    public RobotStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getReloadDelay() {
        return reloadDelay;
    }

    public int getRepairDelay() {
        return repairDelay;
    }


    public void setShots(int shots) {
        this.shots = shots;
    }

    public void fireShot() {
        if (this.shots > 0) {
            this.shots--;
        }else {
            throw new IllegalStateException("No more shots left");
        }

        }



    public void setShields(int shields) {
        this.shields = shields;
    }


    // Code from Alwaba below
    public IWorld.UpdateResponse updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) { //if up and command = forward then + else - (?)
            newY = newY + nrSteps;
        }
        if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }
        if (Direction.EAST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }
        if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(this.TOP_LEFT,this. BOTTOM_RIGHT)) {
            this.position = newPosition;
            return IWorld.UpdateResponse.SUCCESS;
        } else if (!newPosition.isIn(this.TOP_LEFT, this.BOTTOM_RIGHT)) {
            return IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD;
        }
        return IWorld.UpdateResponse.SUCCESS;
    }

    public int getAmmo() {
        return this.shots;
    }

    public World getWorld() {
        return world;
    }


    public boolean isAtEdge() {
        return false;
    }

    public boolean isAtWestEdge() {
        return false;
    }

    public boolean isAtSouthEdge() {
        return false;
    }

    public boolean isAtEastEdge() {
        return false;
    }

    public boolean isAtNorthEdge() {
        return false;
    }
}
