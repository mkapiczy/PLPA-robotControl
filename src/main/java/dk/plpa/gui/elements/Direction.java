package dk.plpa.gui.elements;

public enum Direction {
    NORTH ("N"),
    SOUTH ("S"),
    EAST ("E"),
    WEST ("W");

    private final String value;


    Direction(String e) {
        value = e;
    }

    public String getValue() {
        return value;
    }
}