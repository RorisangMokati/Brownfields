package za.co.wethinkcode.robotworlds.Server.ServerManager;

import za.co.wethinkcode.database.SQLiteDB;
import za.co.wethinkcode.database.DataAccessInterface;
import za.co.wethinkcode.database.WorldDO;
import za.co.wethinkcode.database.ObstacleDO;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.World.World;
import net.lemnik.eodsql.QueryTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveCommand extends ServerCommands {
    private final World world;
    private String worldName;

    public SaveCommand(World world) {
        this.world = world;
        this.worldName = null;
        execute();
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the name for your world: ");
            worldName = scanner.nextLine();

            if (SQLiteDB.worldNameExists(worldName)) {
                System.out.println("World name already exists. Please choose a different name.");
            } else {
                break;
            }
        }

        int worldSize = world.getWorldWidth();
        List<int[]> obstacles = new ArrayList<>();
        for (Obstacle obstacle : world.getObstaclesInWorld()) {
            obstacles.add(new int[]{obstacle.getPosition().getX(), obstacle.getPosition().getY()});
        }
        saveRobotWorld(worldSize, worldName, obstacles.toArray(new int[0][]));
    }

    public void saveRobotWorld(int worldSize, String worldName, int[][] obstacles) {
        if (worldSize <= 0) {
            System.out.println("Invalid world size. Please provide a positive integer: ");
            return;
        }

        if (obstacles == null || obstacles.length == 0) {
            System.out.println("No obstacles provided.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(SQLiteDB.DATABASE_URL)) {
            DataAccessInterface dao = QueryTool.getQuery(conn, DataAccessInterface.class);
            String obstaclesString = convertObstaclesToString(obstacles);
            WorldDO world = new WorldDO(worldName, worldSize, obstaclesString);
            dao.saveWorld(world);

            for (int[] obstacle : obstacles) {
                ObstacleDO obstacleDO = new ObstacleDO(world.getId(), obstacle[0], obstacle[1]);
                dao.saveObstacle(obstacleDO);
            }
            System.out.println("RobotWorld data saved successfully.");
        } catch (SQLException e) {
            System.out.println("Error saving RobotWorld data: " + e.getMessage());
        }
    }

    private String convertObstaclesToString(int[][] obstacles) {
        StringBuilder sb = new StringBuilder();
        for (int[] obstacle : obstacles) {
            sb.append("(").append(obstacle[0]).append(",").append(obstacle[1]).append("),");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }
}