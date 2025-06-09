package ClientAppTests;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Server.Commands.BackCommand;
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

public class BackCommandTest {
    private RobotCreator mockRobotCreator;
    private Robot mockRobot;
    private Position mockPosition;
    private BackCommand backCommand;

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
        when(mockRobot.updatePosition(-5)).thenReturn(IWorld.UpdateResponse.SUCCESS);
        when(mockRobot.getCurrentDirection()).thenReturn(Direction.NORTH);

        backCommand = new BackCommand(args, mockRobotCreator);
        JSONObject response = backCommand.execute(mockRobotCreator);

        assertEquals("OK", response.getString("result"));
        assertEquals("", response.getJSONObject("data").getString("message"));
    }

    @Test
    public void testExecuteFailedOutsideWorld() {
        List<String> args = Arrays.asList("5");
        when(mockRobot.updatePosition(-5)).thenReturn(IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD);
        when(mockRobot.getCurrentDirection()).thenReturn(Direction.NORTH);

        backCommand = new BackCommand(args, mockRobotCreator);
        JSONObject response = backCommand.execute(mockRobotCreator);

        assertEquals("OK", response.getString("result"));
        assertEquals("At South Edge", response.getJSONObject("data").getString("message"));
    }
}