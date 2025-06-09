package za.co.wethinkcode.robotworlds.Server.ServerManager;

import za.co.wethinkcode.robotworlds.Server.World.World;

public abstract class ServerCommands {

    public static void create(String instruction, World world, String worldName) {

        switch (instruction) {
            case "quit":
                new QuitCommand(true);
                return;
            case "robot":
                new RobotsCommand(world);
                return;
            case "dump":
                new DumpCommand(world);
                return;
            case "save":
                new SaveCommand(world);
                return;
            case "restore":
                new RestoreCommand(worldName);
                return;
            default:
                throw new IllegalArgumentException("Not a valid command: " + instruction);
        }
    }

}