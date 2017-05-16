package dk.plpa.scheme;


import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.robot.RobotState;

import java.util.List;

public class LoadCommandsSchemeProcedure {

    private static SchemeProcedure loadProcedure = new SchemeProcedure("loadCommands");

    public static void loadProcedures(RobotState startingPosition, List<CommandListItem> commands) {
        String s = SchemeTypesMapper.createLoadCommandSchemeArgument(startingPosition, commands);
        loadProcedure.apply1(s);
    }
}
