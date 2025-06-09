package za.co.wethinkcode.robotworlds.Server.RobotServer;

import za.co.wethinkcode.robotworlds.Server.World.Obstacles.Obstacles;
import za.co.wethinkcode.robotworlds.Server.ServerManager.RestoreCommand;
import za.co.wethinkcode.robotworlds.Server.ServerManager.ServerManager;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.WorldConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {

    private final ServerSocket serverSocket;
    private static World world;
    private static String worldName = "";

    /**
     * Constructor for MultiServer.
     *
     * @param serverSocket The ServerSocket object to handle incoming connections.
     * @param world The World object representing the robot world.
     * @param worldName The name of the world.
     */
    public MultiServer(ServerSocket serverSocket, World world, String worldName) {
        this.serverSocket = serverSocket;
        this.world = world;
        this.worldName = worldName;
    }

    //Method to start the server accepting robot connections
    public void startServers() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");

                Runnable robot = new RobotServer(socket, this.world);
                Thread task = new Thread(robot);
                task.start();
            }
        } catch(IOException e){
            closeServeSocket();
        }
    }

    //Method to close the server socket when all robots have left the world
    public void closeServeSocket () {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println(" ");
        }
    }

    //The entry point responsible for initializing the server
    public static void main(String[] args) throws IOException {
        CommandLineArguments.processArguments(args);
        ServerSocket serverSocket = new ServerSocket(RobotServer.PORT);
        System.out.println("Server running & waiting for robots to connect...");
        WorldConfig worldConfig = new WorldConfig();
        worldConfig.setUpWorld();
        World world = new World(worldConfig.extractDataFromConfigFile());
        world.setObstaclesInWorld(Obstacles.generateObstacles(world));
        MultiServer servers = new MultiServer(serverSocket, world, worldName);
        ServerManager serverManager = new ServerManager(serverSocket, world, worldName);
        serverManager.start();
        servers.startServers();

        if (!worldName.isEmpty()) {
            new RestoreCommand(worldName);
        }
    }

    public static World getWorld() {
        return world;
    }
}