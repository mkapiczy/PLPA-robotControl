package dk.plpa.testUtils;

import dk.plpa.gui.listViewsComponents.CommandEnum;
import dk.plpa.gui.listViewsComponents.CommandListItem;

import java.util.ArrayList;
import java.util.List;

public class CommandsMockCreator {

    public static List<CommandListItem> createMockListOfCommandListItems() {
        List<CommandListItem> commands = new ArrayList<>();
        commands.add(new CommandListItem(CommandEnum.MOVE_FORWARD, "8"));
        commands.add(new CommandListItem(CommandEnum.TURN_LEFT));
        commands.add(new CommandListItem(CommandEnum.MOVE_FORWARD, "7"));
        return commands;
    }
}
