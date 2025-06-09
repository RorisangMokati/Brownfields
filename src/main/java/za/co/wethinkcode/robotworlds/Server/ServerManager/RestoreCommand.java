package za.co.wethinkcode.robotworlds.Server.ServerManager;

import net.lemnik.eodsql.QueryTool;
import za.co.wethinkcode.database.DataAccessInterface;
import za.co.wethinkcode.database.ObstacleDO;
import za.co.wethinkcode.database.SQLiteDB;
import za.co.wethinkcode.database.WorldDO;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.RobotServer.MultiServer;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.Position;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestoreCommand extends ServerCommands {
    public World world;

    public RestoreCommand(String worldName) {
        this.world = MultiServer.getWorld();
        execute(worldName);
    }

    public void execute(String worldName) {
        List<String> worldNames = SQLiteDB.getWorldNames();
        if (worldNames.isEmpty()) {
            System.out.println("No worlds available to restore.");
            return;
        }

        System.out.println("Available worlds:");
        for (String name : worldNames) {
            System.out.println("- " + name);
        }

        Scanner scanner = new Scanner(System.in);
        while (worldName == null || worldName.isEmpty() || !SQLiteDB.worldNameExists(worldName)) {
            if (worldName != null && !worldName.isEmpty()) {
                System.out.println("World name does not exist. Please choose a valid name.");
            }
            System.out.print("Enter the name of the world to restore: ");
            worldName = scanner.nextLine();
        }

        try (Connection conn = DriverManager.getConnection(SQLiteDB.DATABASE_URL)) {
            DataAccessInterface dao = QueryTool.getQuery(conn, DataAccessInterface.class);
            WorldDO worldDO = dao.getWorld(worldName);
            List<ObstacleDO> obstaclesDO = SQLiteDB.getObstacles(worldDO.getId());

            List<Obstacle> obstacles = new ArrayList<>();
            for (ObstacleDO obstacleDO : obstaclesDO) {
                Position position = new Position(obstacleDO.getX(), obstacleDO.getY());
                obstacles.add(new SquareObstacle(position));
            }

            world.setWorldWidth(worldDO.getSize());
            world.setWorldHeight(worldDO.getSize());
            world.setObstaclesInWorld(obstacles);

            System.out.println("World " + worldName + " restored");
        } catch (SQLException e) {
            System.out.println("Error restoring world: " + e.getMessage());
        }
    }
}