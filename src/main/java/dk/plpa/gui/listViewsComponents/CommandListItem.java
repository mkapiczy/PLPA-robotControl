package dk.plpa.gui.listViewsComponents;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandListItem {
    CommandEnum command;
    String commandParam;

    public CommandListItem(CommandEnum command) {
        this.command = command;
        this.commandParam = "1";
    }

    @Override
    public String toString() {
        return this.command.getCommandName();
    }
}