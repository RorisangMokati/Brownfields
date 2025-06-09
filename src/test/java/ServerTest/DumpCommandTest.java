
package ServerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.ServerManager.DumpCommand;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.Server.World.Obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.Server.World.Position;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DumpCommandTest {

    private World mockWorld;
    private DumpCommand dumpCommand;

    @BeforeEach
    public void setUp() {
        mockWorld = mock(World.class);
        when(mockWorld.getWorldHeight()).thenReturn(10);
        when(mockWorld.getWorldWidth()).thenReturn(20);
        when(mockWorld.getVisibility()).thenReturn(5);

        // Mocking obstacles
        Obstacle obstacle1 = new SquareObstacle(new Position(1, 1));
        Obstacle obstacle2 = new SquareObstacle(new Position(2, 2));
        List<Obstacle> obstacles = Arrays.asList(obstacle1, obstacle2);
        when(mockWorld.getObstaclesInWorld()).thenReturn(obstacles);

        dumpCommand = new DumpCommand(mockWorld);
    }

    @Test
    public void testDumpWithNoRobots() {
        when(mockWorld.getRobotsInWorld()).thenReturn(Collections.emptyList());

        String expectedOutput = "\nWorld height: 10\nWorld width: 20\nWorld area: 200\nArea visible to robot: 5\n\n Obstacles: \n[(1,1), (2,2)]";
        String actualOutput = dumpCommand.dump();

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testDumpWithRobots() {
        Robot mockRobot1 = mock(Robot.class);
        when(mockRobot1.getName()).thenReturn("Robot1");

        Robot mockRobot2 = mock(Robot.class);
        when(mockRobot2.getName()).thenReturn("Robot2");

        List<Robot> robotList = Arrays.asList(mockRobot1, mockRobot2);
        when(mockWorld.getRobotsInWorld()).thenReturn(robotList);

        String expectedOutput = "\nWorld height: 10\nWorld width: 20\nWorld area: 200\nArea visible to robot: 5\n\n Obstacles: \n[(1,1), (2,2)]";
        String actualOutput = dumpCommand.dump();

        assertEquals(expectedOutput, actualOutput);
    }
}