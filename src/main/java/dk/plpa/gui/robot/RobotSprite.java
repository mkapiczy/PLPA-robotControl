package dk.plpa.gui.robot;


import dk.plpa.gui.floorComponents.FloorPane;
import dk.plpa.gui.floorComponents.Tile;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotPosition;
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
    private RobotPosition robotPosition;
    @Setter(AccessLevel.NONE)
    private boolean isInitialPositionSet = false;

    public RobotSprite() {
        this.robotImg = new Image("robot.png", 15, 15, true, true);
    }

    public void setRobotInitialPosition(RobotPosition initialPosition) {
        this.robotPosition = initialPosition;
        this.isInitialPositionSet = true;
    }

    public List<RobotPosition> moveRobotTo(RobotPosition destinedPosition) {
        if (isInitialPositionSet) {
            List<RobotPosition> movementPositions = new ArrayList<>();

            int destinationX = destinedPosition.getX();
            int destinationY = destinedPosition.getY();
            RobotDirectionEnum destinationDirection = destinedPosition.getDirection();

            int currentRobotXPosition = this.robotPosition.getX();
            int currentRobotYPosition = this.robotPosition.getY();

            if (destinationX != currentRobotXPosition) {
                movementPositions = moveHorizontaly(currentRobotXPosition, destinationY);
            } else if (destinationY != currentRobotYPosition) {
                movementPositions = moveVertically(currentRobotYPosition, destinationY);
            } else if (!this.robotPosition.getDirection().equals(destinationDirection)) {
                movementPositions = rotate(destinationDirection);
            }
            return movementPositions;
        } else {
            log.error("Before moving robot the initial position needs to be set up!");
            return null;
        }
    }

    private List<RobotPosition> moveHorizontaly(int currentRobotXPosition, int destinationX) {
        List<RobotPosition> movementPositions = new ArrayList<>();

        int xDifference = Math.abs(destinationX - currentRobotXPosition);

        if (destinationX >= currentRobotXPosition) {
            for (int i = 0; i <= xDifference; i++) {
                int nextTileX = currentRobotXPosition + i;
                movementPositions.add(new RobotPosition(nextTileX, this.getRobotPosition().getY(), this.getRobotPosition().getDirection()));
            }
        } else {
            for (int i = 0; i <= xDifference; i++) {
                int nextTileX = currentRobotXPosition - i;
                movementPositions.add(new RobotPosition(nextTileX, this.getRobotPosition().getY(), this.getRobotPosition().getDirection()));
            }
        }
        return movementPositions;
    }

    private List<RobotPosition> moveVertically(int currentRobotYPosition, int destinationY) {
        List<RobotPosition> movementPositions = new ArrayList<>();

        int diff = Math.abs(destinationY - currentRobotYPosition);
        if (destinationY > currentRobotYPosition) {
            for (int i = 0; i <= diff; i++) {
                int nextTileY = currentRobotYPosition + i;
                movementPositions.add(new RobotPosition(this.getRobotPosition().getX(), nextTileY, this.getRobotPosition().getDirection()));
            }
        } else {
            for (int i = 0; i <= diff; i++) {
                int nextTileY = currentRobotYPosition - i;
                movementPositions.add(new RobotPosition(this.getRobotPosition().getX(), nextTileY, this.getRobotPosition().getDirection()));
            }
        }

        return movementPositions;
    }

    private List<RobotPosition> rotate(RobotDirectionEnum destinationDirection) {
        List<RobotPosition> movementPositions = new ArrayList<>();
        // TODO actual rotation step by step
        movementPositions.add(new RobotPosition(this.getRobotPosition().getX(), this.getRobotPosition().getY(), destinationDirection));
        return movementPositions;
    }

    public void draw(GraphicsContext gc, FloorPane floor) {
        Tile tile = floor.getTileByPosition(this.getRobotPosition().getX(), this.getRobotPosition().getY());
        gc.clearRect(0, 0, 512, 512);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.drawImage(this.robotImg, tile.getLayoutX(), tile.getLayoutY());
    }
}
