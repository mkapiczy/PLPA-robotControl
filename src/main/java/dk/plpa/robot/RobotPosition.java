package dk.plpa.robot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RobotPosition {
    private int xCoord;
    private int yCoord;
    private RobotDirectionEnum direction;

    public RobotPosition(int xCoord, int yCoord, RobotDirectionEnum direction) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.direction = direction;
    }

    public RobotPosition() {

    }
}
