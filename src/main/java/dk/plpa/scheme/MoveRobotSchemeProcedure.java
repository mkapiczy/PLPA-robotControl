package dk.plpa.scheme;

import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotState;
import gnu.lists.Pair;
import gnu.math.IntNum;

import java.util.Arrays;
import java.util.List;

public class MoveRobotSchemeProcedure {

    private static SchemeProcedure moveRobotProcedure = new SchemeProcedure("moveRobot");

    public static RobotState moveRobot() {
        Object schemeResult = moveRobotProcedure.apply0();
        List robotState = Arrays.asList(((Pair) schemeResult).toArray());
        int x = ((IntNum) robotState.get(0)).intValue();
        int y = ((IntNum) robotState.get(1)).intValue();
        String direction = robotState.get(2).toString();
        int errorcode = ((IntNum) robotState.get(3)).intValue();
        String carriedObject = robotState.get(4).toString();

        return new RobotState(x, y, RobotDirectionEnum.findByValue(direction), errorcode, carriedObject);
    }

}
