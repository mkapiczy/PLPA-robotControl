package dk.plpa.scheme;


import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.robot.RobotPosition;

import java.util.List;

public class LoadCommandsSchemeProcedure {

    private static SchemeProcedure loadProcedure = new SchemeProcedure("loadCommands");

    public static void loadProcedures(RobotPosition startingPosition, List<CommandListItem> commands) {
        // TODO Something is wrong with loading the procedures. Mapped procedure names strings are not recognized by Scheme.
//        String s = SchemeTypesMapper.createLoadCommandSchemeArgument(startingPosition, commands);
        String s = "(list (list 0 8 \"E\") (list MoveForward 8) (list TurnLeft 1) (list MoveForward 7) (list TurnLeft 1) (list MoveForward 2) (list TurnLeft 1) (list PickObject \"P1\") (list TurnLeft 1) (list MoveForward 2) (list TurnRight 1) (list DropObject 1) )";

        loadProcedure.apply1(s);
    }
}
