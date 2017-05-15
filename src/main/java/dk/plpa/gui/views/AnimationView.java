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
import javafx.scene.control.Alert;
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
    private RobotProgrammingView robotProgrammingView;
    private CommandsListView commandsListView;

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
        group.getChildren().addAll(floor, this.getCanvas());
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
            private RobotSprite robotSprite = new RobotSprite();
            private long lastUpdate = 0;
            private int calledSchemeProcedure = 0;
            private boolean shouldCallNextSchemeProcedure = true;
            private boolean errorOccured = false;
            private List<RobotState> robotMiddleStates = new ArrayList<>();

            public void handle(long currentNanoTime) {
                setOtherViewsMouseTransparent();

                if (currentNanoTime - lastUpdate >= animationInterval) {
                    if (shouldCallNextSchemeProcedure) {
                        RobotState nextRobotState = MoveRobotSchemeProcedure.moveRobot();
                        robotProgrammingView.highlightCurrentlyRunningProcedure(calledSchemeProcedure);

                        if (nextRobotState.isRobotRunningOK() || nextRobotState.isRobotInErrorState()) {
                            if (calledSchemeProcedure == 0) {
                                robotSprite.setRobotInitialState(nextRobotState);
                            } else {
                                robotMiddleStates = robotSprite.moveRobotTo(nextRobotState);
                                shouldCallNextSchemeProcedure = false;
                                if (nextRobotState.isRobotInErrorState()) {
                                    errorOccured = true;
                                }
                            }
                        } else if (nextRobotState.isRobotTurnedOff()) {
                            endAnimation();
                            displayAnimationFinishedDialog();
                        }
                        calledSchemeProcedure++;
                    }

                    if (!robotMiddleStates.isEmpty()) {
                        setNextRobotState();
                    } else {
                        if (errorOccured) {
                            endAnimation();
                            displayErrorAlert();
                        } else {
                            shouldCallNextSchemeProcedure = true;
                        }
                    }

                    robotSprite.draw(gc, floor);
                    lastUpdate = currentNanoTime;
                }

            }

            private void setNextRobotState() {
                RobotState nextState = robotMiddleStates.get(0);
                robotSprite.setRobotState(nextState);
                robotMiddleStates.remove(nextState);
            }

            private void endAnimation() {
                this.stop();
                this.
                setOtherViewsAvailable();
            }

            private void setOtherViewsMouseTransparent() {
                robotProgrammingView.setMouseTransparent(true);
                commandsListView.setMouseTransparent(true);
            }

            private void setOtherViewsAvailable() {
                robotProgrammingView.setMouseTransparent(false);
                commandsListView.setMouseTransparent(false);
            }

            private void displayErrorAlert() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Robot reached not allowed state!");
                alert.setContentText("Robot reached not allowed state!");
                alert.showAndWait();
            }

            private void displayAnimationFinishedDialog() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Animation finished!");
                alert.setHeaderText("Animation finished!");
                alert.setContentText("Animation finished!");
                alert.showAndWait();
            }
        }.start();

    }


}
