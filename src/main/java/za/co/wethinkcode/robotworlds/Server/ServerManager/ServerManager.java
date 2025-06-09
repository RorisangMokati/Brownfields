package za.co.wethinkcode.robotworlds.Server.ServerManager;

import za.co.wethinkcode.robotworlds.Server.World.World;

import java.util.Scanner;
import java.net.ServerSocket;

public class ServerManager extends Thread {
    private World world;
    private final String worldName;
    private final ServerSocket serverSocket;
    private final Scanner scanner = new Scanner(System.in);


    public ServerManager(ServerSocket serverSocket, World world, String worldName){
        this.serverSocket = serverSocket;
        this.world = world;
        this.worldName = worldName;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()){
            try{
                String command = getCommand();
                ServerCommands.create(command, world, worldName);

            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public String getCommand(){
        if (scanner.hasNext()) {
            return scanner.nextLine();
        }
        return "";
    }
}
