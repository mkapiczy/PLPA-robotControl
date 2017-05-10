package dk.plpa.robot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RobotPosition {
    private int x;
    private int y;
    private RobotDirectionEnum direction;

    public RobotPosition(int x, int y, RobotDirectionEnum direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

}
