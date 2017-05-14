package dk.plpa.robot;


public enum RobotCodesEnum {
    OK(0),
    ERROR(-1),
    TURNED_OFF(1);

    private int errorCode;

    RobotCodesEnum(final int errorCode) {
        this.errorCode = errorCode;
    }

    public int getValue() {
        return this.errorCode;
    }

}
