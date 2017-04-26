package dk.plpa.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandMapper {


    private static final int MOVE_FORWARD = 1;
    private static final int TURN_RIGT = 2;
    private static final int TURN_LEFT = 3;
    private static final int PICK_OBJECT = 4;
    private static final int DROP_OBJECT = 5;

    public static List<List<Object>> createACommandList() {
        List<List<Object>> commands = new ArrayList<>();

        List<Object> moveForwardTwoSteps = getMoveForwardCommand(2);
        List<Object> turnRight = getTurnRightCommand();
        List<Object> turnLeft = getTurnLeftCommand();
        List<Object> pickObject = getPickObjectCommand("P1");
        List<Object> dropObject = getDropObjectCommand();

        commands.add(moveForwardTwoSteps);
        commands.add(turnRight);
        commands.add(turnLeft);
        commands.add(pickObject);
        commands.add(dropObject);

        return commands;
    }

    private static List<Object> getMoveForwardCommand(int numberOfSteps) {
        return Arrays.asList(MOVE_FORWARD, numberOfSteps);
    }

    private static List<Object> getTurnRightCommand() {
        return Arrays.asList(TURN_RIGT, null);
    }

    private static List<Object> getTurnLeftCommand() {
        return Arrays.asList(TURN_LEFT, null);
    }

    private static List<Object> getPickObjectCommand(String objectTiPick) {
        return Arrays.asList(PICK_OBJECT, objectTiPick);
    }

    private static List<Object> getDropObjectCommand() {
        return Arrays.asList(DROP_OBJECT, null);
    }
}
