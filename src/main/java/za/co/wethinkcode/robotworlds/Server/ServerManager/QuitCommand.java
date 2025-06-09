package za.co.wethinkcode.robotworlds.Server.ServerManager;


public class QuitCommand extends ServerCommands{
    private final boolean shouldExit;

    public QuitCommand(boolean shouldExit) {
        this.shouldExit = shouldExit;
        execute();
    }

    //Shuts down the server
    public void execute() {
        System.out.println("Shutting Down The Server...");
        if (shouldExit) {
            System.exit(0);
        }
    }
}
