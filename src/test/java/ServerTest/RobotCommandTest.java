package ServerTest;

import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import za.co.wethinkcode.robotworlds.Server.World.World;
import static org.junit.jupiter.api.Assertions.assertEquals;
import za.co.wethinkcode.robotworlds.Server.World.WorldConfig;
import za.co.wethinkcode.robotworlds.Server.ServerManager.RobotsCommand;

public class RobotCommandTest {

    private final WorldConfig worldConfig = new WorldConfig();
    private final World world = new World(worldConfig.extractDataFromConfigFile());
    private final RobotsCommand robotsCommand = new RobotsCommand(world);

    @Test
    void robotCommandTest() {
        ByteArrayOutputStream robotPrint = new ByteArrayOutputStream();
        System.setOut(new PrintStream(robotPrint));

        // Execute the command
        this.robotsCommand.execute();

        // Verify output
        assertEquals("No robots connected!\n", robotPrint.toString());

        // Reset System.out
        System.setOut(System.out);
    }
}
