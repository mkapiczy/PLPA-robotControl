package dk.plpa.scheme;

import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotPosition;
import gnu.lists.Pair;
import gnu.math.IntNum;

import java.util.Arrays;
import java.util.List;

public class MoveRobotSchemeProcedure {

    private static SchemeProcedure moveRobotProcedure = new SchemeProcedure("moveRobot");

    public static RobotPosition moveRobot() {
        Object schemeResult = moveRobotProcedure.apply0();
        List robotState = Arrays.asList(((Pair) schemeResult).toArray());
        int x = ((IntNum) robotState.get(0)).intValue();
        int y = ((IntNum) robotState.get(1)).intValue();
        String direction = robotState.get(2).toString();

        return new RobotPosition(x, y, RobotDirectionEnum.findByValue(direction));
    }

}
