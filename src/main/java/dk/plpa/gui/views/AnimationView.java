package dk.plpa.gui.views;


import dk.plpa.gui.floorComponents.FloorPane;
import dk.plpa.gui.robot.RobotSprite;
import dk.plpa.robot.RobotState;
import dk.plpa.scheme.MoveRobotSchemeProcedure;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class AnimationView extends AbstractView {

    private FloorPane floor;
    private Button runSimulationButton;
    private VBox vBox = new VBox();
    private RobotSprite robotSprite = new RobotSprite();

    public AnimationView(double width, double height) {
        super(width, height);
    }

    @Override
    public void setBackground(Node background) {
        super.setBackground(background);
        if (background instanceof FloorPane) {
            this.floor = (FloorPane) background;
        }
    }

    public void setUpViewElements() {
        runSimulationButton = new Button("Run simulation");
        runSimulationButton.setPadding(new Insets(25, 25, 25, 25));
        Group group = new Group();
        group.getChildren().addAll(floor, getCanvas());
        vBox.getChildren().addAll(group, runSimulationButton);
        vBox.setAlignment(Pos.CENTER);
        this.getChildren().clear();
        this.getChildren().add(vBox);
        runSimulationButton.setOnMouseClicked(event -> runAnimation());
    }

    private void runAnimation() {
        GraphicsContext gc = this.getCanvas().getGraphicsContext2D();

        new AnimationTimer() {
            private final int animationInterval = 500000000;
            private long lastUpdate = 0;
            private int calledSchemeProcedure = 0;
            private boolean shouldCallNextSchemeProcedure = true;
            private List<RobotState> robotMiddleStates = new ArrayList<>();

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= animationInterval) {
                    if (shouldCallNextSchemeProcedure) {
                        RobotState nextRobotState = MoveRobotSchemeProcedure.moveRobot();
                        if (calledSchemeProcedure == 0) {
                            robotSprite.setRobotInitialState(nextRobotState);
                        } else {
                            robotMiddleStates = robotSprite.moveRobotTo(nextRobotState);
                            shouldCallNextSchemeProcedure = false;
                        }
                        calledSchemeProcedure++;
                    }

                    if (!robotMiddleStates.isEmpty()) {
                        RobotState nextState = robotMiddleStates.get(0);
                        robotSprite.setRobotState(nextState);
                        robotMiddleStates.remove(nextState);
                    } else {
                        shouldCallNextSchemeProcedure = true;
                    }

                    if (calledSchemeProcedure == 20) {
                        this.stop();
                    }

                    robotSprite.draw(gc, floor);
                    lastUpdate = currentNanoTime;
                }

            }
        }.start();
    }


}
