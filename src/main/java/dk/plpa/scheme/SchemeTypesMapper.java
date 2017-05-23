package dk.plpa.scheme;


import dk.plpa.gui.listViewsComponents.CommandEnum;
import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.robot.RobotState;

import java.util.List;
import java.util.Objects;

public class SchemeTypesMapper {



    public static <T> String createLoadCommandSchemeArgument(RobotState startingPosition, List<CommandListItem> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            StringBuilder s = new StringBuilder();
            s.append("(list (list " + startingPosition.getX() + " " + startingPosition.getY() + "\"" + startingPosition.getDirection().getValue() + "\"" +") ");

            for (int i = 0; i < javaList.size(); i++) {
                String commandName = javaList.get(i).getCommand().getSchemeCommand().replace(" ", "");
                String commandParam;
                if(Objects.equals(commandName, CommandEnum.PICK_OBJECT.getSchemeCommand())) {
                    commandParam = (javaList.get(i).getCommandParam().isEmpty() ? "\"obj\"" : "\"" + javaList.get(i).getCommandParam() + "\"");
                } else {
                    commandParam = (javaList.get(i).getCommandParam().isEmpty() ? "1" : javaList.get(i).getCommandParam());
                }
                s.append("(list " + commandName + " " + commandParam + ")");
                s.append(" ");
            }
            s.append("(list TurnOffRobot 1) ");
            s.append(')');
            return s.toString();
        }
    }
}
