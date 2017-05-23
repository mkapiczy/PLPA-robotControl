package dk.plpa.scheme;

import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotState;
import dk.plpa.testUtils.CommandsMockCreator;
import gnu.mapping.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class LoadCommandsSchemeProcedureTest {

    @Before
    public void setUpTestEnvironment() {
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();
    }

    @Test
    public void testLoadProcedures() throws Exception {
        RobotState startingPosition = new RobotState(0, 8, RobotDirectionEnum.EAST, 0, "");
        List<CommandListItem> commands = CommandsMockCreator.createMockListOfCommandListItems();
        LoadCommandsSchemeProcedure.loadProcedures(startingPosition, commands);
        assertTrue(AreCommandsLoadedSchemeProcedure.areCommandsLoadedToRobot());
    }

    @After
    public void cleanUp() {
        Environment.setCurrent(null);
    }

}