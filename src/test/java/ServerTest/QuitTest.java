package ServerTest;

import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import za.co.wethinkcode.robotworlds.Server.ServerManager.QuitCommand;

public class QuitTest {
    private final QuitCommand quit = new QuitCommand(false);

    @Test
    public void testQuitCommand() {
        // This ensures that the system does not exit so that the test works.
        ByteArrayOutputStream quitPrint = new ByteArrayOutputStream();
        System.setOut(new PrintStream(quitPrint));

        quit.execute();
        assertEquals("Shutting Down The Server...\n", quitPrint.toString());
    }
}
