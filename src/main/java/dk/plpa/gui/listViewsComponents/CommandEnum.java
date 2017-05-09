package dk.plpa.gui.listViewsComponents;

public enum CommandEnum {
    MOVE_FORWARD("MOVE FORWARD","MoveForward"),
    TURN_RIGHT("TURN RIGHT","TurnRight"),
    TURN_LEFT("TURN LEFT","TurnLeft"),
    PICK_OBJECT("PICK OBJECT","PickObject"),
    DROP_OBJECT("DROP OBJECT","DropObject");

    private final String commandName;
    private final String schemeCommand;

    CommandEnum(String commandName ,String schemeCommand) {
        this.commandName = commandName;
        this.schemeCommand = schemeCommand;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getSchemeCommand() {
        return schemeCommand;
    }

    public static CommandEnum getFromCommandName(String name) {
        for (CommandEnum e : CommandEnum.values()) {
            if (e.commandName.equalsIgnoreCase(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException("CommandEnum with name " + name + " does not exist");
    }
}
