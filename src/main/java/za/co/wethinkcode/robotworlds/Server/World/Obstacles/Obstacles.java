package za.co.wethinkcode.robotworlds.Server.World.Obstacles;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.Position;

public class Obstacles {

    private static final ArrayList<Obstacle> obstacles = new ArrayList<>();

    /**
     * Generates a list of 10 random square obstacles within the boundaries of the given world.
     * The obstacles are positioned randomly within the world's dimensions.
     *
     * @param world The world object representing the boundaries of the robot world.
     * @return A list of 10 randomly positioned square obstacles.
     */
    public static List<Obstacle> generateObstacles(World world) {
        Random random = new Random();
        for (int i = 0; i <= (int) World.worldCapacity * 0.25 ; i++) { // Generate obstacles that are total of 1/4 of the world size.
            int x = random.nextInt(world.getWorldWidth()-(-world.getWorldWidth())); // Random x within (-100, 100)
            int y = random.nextInt(world.getWorldHeight()-(-world.getWorldHeight())); // Random y within (-200, 200)
            obstacles.add(new SquareObstacle(new Position(x,y)));
        }
        return obstacles;
    }


    public static ArrayList<Obstacle> getHardCodedObstacles() {
        return obstacles;
    }
}
