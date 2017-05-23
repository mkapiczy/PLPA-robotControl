package dk.plpa.scheme;

import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotState;
import dk.plpa.testUtils.CommandsMockCreator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class SchemeTypesMapperTest {

    @Test
    public void testCreateLoadCommandSchemeArgument() throws Exception {
        RobotState startingPosition = new RobotState(0, 8, RobotDirectionEnum.EAST, 0, "");
        List<CommandListItem> commands = CommandsMockCreator.createMockListOfCommandListItems();
        String loadCommandSchemeArgument = SchemeTypesMapper.createLoadCommandSchemeArgument(startingPosition, commands);
        assertEquals(loadCommandSchemeArgument, "(list (list 0 8\"E\") (list MoveForward 8) (list TurnLeft 1) (list MoveForward 7) (list TurnOffRobot 1) )");
    }


}