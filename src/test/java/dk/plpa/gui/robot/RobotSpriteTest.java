package dk.plpa.gui.robot;

import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotState;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static junit.framework.TestCase.assertTrue;


public class RobotSpriteTest {

    private RobotSprite robotSprite;

    @Before
    public void setUpBeforeTests() {
        Image image = Mockito.mock(Image.class);
        this.robotSprite = new RobotSprite(image);
    }

    @Test
    public void testSetRobotInitialState() throws Exception {
        RobotState initialRobotState = new RobotState(0, 0, RobotDirectionEnum.NORTH, 0, "");
        robotSprite.setRobotInitialState(initialRobotState);
        assertTrue(robotSprite.isInitialPositionSet());
    }

    @Test
    public void shouldReturnEmptyListWhenInitialStateIsNotSetBeforeCallingMoveToFunction() throws Exception {
        RobotState destinedState = new RobotState(0,8,RobotDirectionEnum.NORTH,0,"");
        List<RobotState> middleStates = robotSprite.moveRobotTo(destinedState);
        assertTrue(middleStates.isEmpty());
    }

}