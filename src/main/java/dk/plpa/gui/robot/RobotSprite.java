package dk.plpa.gui.robot;


import dk.plpa.gui.floorComponents.FloorPane;
import dk.plpa.gui.floorComponents.Tile;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RobotSprite {

    private static final Logger log = Logger.getLogger(RobotSprite.class);

    @Setter(AccessLevel.NONE)
    private Image robotImg;
    private RobotState robotState;
    @Setter(AccessLevel.NONE)
    private boolean isInitialPositionSet = false;

    public RobotSprite(Image robotImg) {
        this.robotImg = robotImg;
    }

    public void setRobotInitialState(RobotState initialState) {
        this.robotState = initialState;
        this.isInitialPositionSet = true;
    }

    public List<RobotState> moveRobotTo(RobotState destinedPosition) {
        List<RobotState> movementPositions = new ArrayList<>();
        if (isInitialPositionSet) {

            int destinationX = destinedPosition.getX();
            int destinationY = destinedPosition.getY();
            RobotDirectionEnum destinationDirection = destinedPosition.getDirection();

            int currentRobotXPosition = this.robotState.getX();
            int currentRobotYPosition = this.robotState.getY();

            if (destinationX != currentRobotXPosition) {
                movementPositions = moveHorizontaly(currentRobotXPosition, destinationX);
            } else if (destinationY != currentRobotYPosition) {
                movementPositions = moveVertically(currentRobotYPosition, destinationY);
            } else if (!this.robotState.getDirection().equals(destinationDirection)) {
                movementPositions = rotate(destinationDirection);
            } else {
                movementPositions.add(destinedPosition);
            }

        } else {
            log.error("Before moving robot the initial position needs to be set up!");
        }
        return movementPositions;
    }

    private List<RobotState> moveHorizontaly(int currentRobotXPosition, int destinationX) {
        List<RobotState> movementPositions = new ArrayList<>();

        int xDifference = Math.abs(destinationX - currentRobotXPosition);

        for (int i = 0; i <= xDifference; i++) {
            int nextTileX;

            if (destinationX > currentRobotXPosition) {
                nextTileX = currentRobotXPosition + i;
            } else {
                nextTileX = currentRobotXPosition - i;
            }
            RobotState middleState = new RobotState(this.getRobotState());
            middleState.setX(nextTileX);
            movementPositions.add(middleState);
        }

        return movementPositions;
    }

    private List<RobotState> moveVertically(int currentRobotYPosition, int destinationY) {
        List<RobotState> movementPositions = new ArrayList<>();

        int yDifference = Math.abs(destinationY - currentRobotYPosition);

        for (int i = 0; i <= yDifference; i++) {
            int nextTileY;
            if (destinationY > currentRobotYPosition) {
                nextTileY = currentRobotYPosition + i;
            } else {
                nextTileY = currentRobotYPosition - i;
            }
            RobotState middleState = new RobotState(this.getRobotState());
            middleState.setY(nextTileY);
            movementPositions.add(middleState);
        }

        return movementPositions;
    }

    private List<RobotState> rotate(RobotDirectionEnum destinationDirection) {
        List<RobotState> movementPositions = new ArrayList<>();
        // TODO actual rotation step by step
        RobotState middleState = new RobotState(this.getRobotState());
        middleState.setDirection(destinationDirection);
        movementPositions.add(middleState);
        return movementPositions;
    }

    public void draw(GraphicsContext gc, FloorPane floor) {
        Tile tile = floor.getTileByPosition(this.getRobotState().getX(), this.getRobotState().getY());
        gc.clearRect(0, 0, 1024, 1024);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        changeRobotPictureAccordingToRobotDirection(this.getRobotState().getDirection());

        if (this.getRobotState().isRobotCarryingObject()) {
            drawCarriedObject(gc, tile.getLayoutX(), tile.getLayoutY());
        }

        gc.drawImage(this.robotImg, tile.getLayoutX(), tile.getLayoutY());
    }

    public void drawCarriedObject(GraphicsContext gc, double x, double y) {
        gc.fillOval(x, y, 5, 5);
        gc.setFill(Color.RED);
    }

    private void changeRobotPictureAccordingToRobotDirection(RobotDirectionEnum thisEnum) {

        switch (thisEnum) {
            case NORTH:
                this.robotImg = new Image("robot2000north.png", 20, 20, true, true);
                break;
            case SOUTH:
                this.robotImg = new Image("robot2000south.png", 20, 20, true, true);
                break;
            case EAST:
                this.robotImg = new Image("robot2000east.png", 20, 20, true, true);
                break;
            case WEST:
                this.robotImg = new Image("robot2000west.png", 20, 20, true, true);
                break;
            default:
                break;
        }
    }
}
