package dk.plpa.gui.elements;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private int xCoord;
    private int yCoord;
    private Direction direction;

    public Position(int xCoord, int yCoord, Direction direction) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.direction = direction;
    }

    public Position() {

    }
}
