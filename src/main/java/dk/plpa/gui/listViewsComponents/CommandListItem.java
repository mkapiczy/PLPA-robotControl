package dk.plpa.gui.listViewsComponents;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandListItem {
    String commandName;
    String commandParam;

    public CommandListItem(String commandName) {
        this.commandName = commandName;
        commandParam = "";
    }

    @Override
    public String toString() {
        return this.commandName;
    }
}