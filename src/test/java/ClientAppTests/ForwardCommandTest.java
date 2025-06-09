package ClientAppTests;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.Commands.ForwardCommand;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.Direction;
import za.co.wethinkcode.robotworlds.Server.World.IWorld.IWorld;
import za.co.wethinkcode.robotworlds.Server.World.Position;
import za.co.wethinkcode.robotworlds.Server.World.Response;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ForwardCommandTest {
    private RobotCreator mockRobotCreator;
    private Robot mockRobot;
    private Position mockPosition;
    private ForwardCommand forwardCommand;

    @BeforeEach
    public void setUp() {
        mockRobotCreator = mock(RobotCreator.class);
        mockRobot = mock(Robot.class);
        mockPosition = mock(Position.class);

        when(mockRobotCreator.getRobot()).thenReturn(mockRobot);
        when(mockRobot.getPosition()).thenReturn(mockPosition);
        when(mockPosition.getX()).thenReturn(0);
        when(mockPosition.getY()).thenReturn(0);
    }

    @Test
    public void testExecuteSuccess() {
        List<String> args = Arrays.asList("5");
        when(mockRobot.updatePosition(5)).thenReturn(IWorld.UpdateResponse.SUCCESS);
        when(mockRobot.getCurrentDirection()).thenReturn(Direction.NORTH);

        forwardCommand = new ForwardCommand(args, mockRobotCreator);
        JSONObject response = forwardCommand.execute(mockRobotCreator);

        assertEquals("OK", response.getString("result"));
        assertEquals("Done", response.getJSONObject("data").getString("message"));
    }

    @Test
    public void testExecuteFailedOutsideWorld() {
        List<String> args = Arrays.asList("5");
        when(mockRobot.updatePosition(5)).thenReturn(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD);
        when(mockRobot.getCurrentDirection()).thenReturn(Direction.NORTH);

        forwardCommand = new ForwardCommand(args, mockRobotCreator);
        JSONObject response = forwardCommand.execute(mockRobotCreator);

        assertEquals("OK", response.getString("result"));
        assertEquals("At the NORTH edge", response.getJSONObject("data").getString("message"));
    }
}