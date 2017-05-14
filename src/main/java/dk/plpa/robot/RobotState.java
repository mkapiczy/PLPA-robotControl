package dk.plpa.robot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RobotState {
    private int x;
    private int y;
    private RobotDirectionEnum direction;
    private int errorCode = 0;
    private String carriedObject;


    public RobotState(int x, int y, RobotDirectionEnum direction, int errorCode, String carriedObject) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.errorCode = errorCode;
        this.carriedObject = carriedObject;
    }

    public RobotState(RobotState stateToCopy) {
        this.x = stateToCopy.getX();
        this.y = stateToCopy.getY();
        this.direction = stateToCopy.getDirection();
        this.errorCode = stateToCopy.getErrorCode();
        this.carriedObject = stateToCopy.getCarriedObject();
    }

}