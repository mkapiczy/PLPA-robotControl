package dk.plpa.scheme;


import dk.plpa.gui.elements.Command;
import dk.plpa.gui.elements.Position;

import java.util.List;
import java.util.Objects;

public class SchemeTypesMapper {


    public static <T> String javaListToSchemeList(List<T> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            String s = "'(" + javaList.toString().replace(",", "").replace("[", "").replace("]", "") + ')';
            return s;
        }
    }

    public static <T> String javaTwoElementListToSchemeCons(List<T> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else if(javaList.size()==2){
            String s = "(cons " + javaList.toString().replace(",", "").replace("[", "").replace("]", "").replace("null","'()") + ')';
            return s;
        } else{
            return null;
        }
    }


    public static <T> String javaListOfListToSchemeListOfPairs(List<List<T>> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            String s = "'(";
            for (int i = 0; i < javaList.size(); i++) {
                    s += javaTwoElementListToSchemeCons(javaList.get(i));
                    s+= " ";
            }
            s += ')';
            return s;
        }
    }

    public static <T> String createLoadCommandSchemeArgument(Position startingPosition, List<Command> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            StringBuilder s = new StringBuilder();
            s.append("(list (list " + startingPosition.getXCoord() + " " + startingPosition.getYCoord() + "\"" + startingPosition.getDirection().getValue() + "\"" +") ");

            for (int i = 0; i < javaList.size(); i++) {
                String command = javaList.get(i).getName().replace(" ", "");
                String arg;
                if(Objects.equals(command, "PICKOBJECT")) {
                    arg = (javaList.get(i).getArg().isEmpty() ? "\"obj\"" : "\"" + javaList.get(i).getArg() + "\"");
                } else {
                    arg = (javaList.get(i).getArg().isEmpty() ? "1" : javaList.get(i).getArg());
                }
                s.append("(list \"" + command + "\" " + arg + ")");
                s.append(" ");
            }
            s.append(')');
            return s.toString();
        }
    }
}
