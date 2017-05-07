package dk.plpa.gui.elements;

public enum Direction {
    NORTH ("N",0),
    SOUTH ("S", 180),
    EAST ("E", 90),
    WEST ("W", 270);

    private final String value;
    private int imgRotation = 0;


    Direction(String e, int rotation) {
        value = e;
        this.imgRotation = rotation;
    }

    public String getValue() {
        return value;
    }

    public int getImageRotation () {
        return imgRotation;
    }
}