package dk.plpa;


import dk.plpa.gui.views.ViewsDimensions;
import dk.plpa.gui.floorComponents.FloorPane;
import dk.plpa.gui.floorComponents.FloorRow;
import dk.plpa.gui.floorComponents.Tile;
import dk.plpa.gui.views.AnimationView;
import dk.plpa.gui.views.CommandsListView;
import dk.plpa.gui.views.RobotProgrammingView;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.utils.FloorPaneMapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;


public class App extends Application {

    private Stage theStage;
    private AnimationView animationView;
    private RobotProgrammingView robotProgrammingView;
    private CommandsListView commandsListView;

    public static void main(String[] args) {
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();

        launch(args);
    }


    @Override
    public void start(Stage theStage) throws Exception {
        this.animationView = new AnimationView(ViewsDimensions.ANIMATION_VIEW_WIDTH, ViewsDimensions.VIEWS_HEIGHT);
        this.robotProgrammingView = new RobotProgrammingView(ViewsDimensions.ROBOT_PROGRAMMING_VIEW_WIDTH, ViewsDimensions.VIEWS_HEIGHT);
        this.commandsListView = new CommandsListView(ViewsDimensions.COMMANDS_LIST_VIEW_WIDTH, ViewsDimensions.VIEWS_HEIGHT);
        this.theStage = theStage;

        setUpViews();

        askUserToChooseRobotStartingPosition();

        theStage.show();
    }

    private void setUpViews() {
        Group rootView = new Group();

        Scene scene = new Scene(rootView);
        theStage.setTitle("Robot Control");
        theStage.setScene(scene);
        theStage.setResizable(false);

        FloorPane floor = createFloor();
        animationView.setBackground(floor);

        animationView.setUpViewElements();
        commandsListView.setUpViewElements();
        robotProgrammingView.setUpViewElements();

        SplitPane sidePane = new SplitPane(robotProgrammingView, commandsListView);
        SplitPane mainPane = new SplitPane(animationView, sidePane);

        rootView.getChildren().add(mainPane);
    }

    private void askUserToChooseRobotStartingPosition() {
        robotProgrammingView.setDisable(true);
        commandsListView.setDisable(true);
        animationView.getRunSimulationButton().setDisable(true);

        GraphicsContext gc = robotProgrammingView.getCanvas().getGraphicsContext2D();
        gc.fillText("Choose robot starting position", 100, 100);

        animationView.getCanvas().setMouseTransparent(true);

        for (FloorRow row : animationView.getFloor().getRows()) {
            for (Tile tile : row.getTiles())
                tile.setOnMouseClicked(event -> {
                    if (tile.getFill().equals(Color.RED)) {
                        animationView.getCanvas().setMouseTransparent(false);
                        animationView.getFloor().setMouseTransparent(true);
                        robotProgrammingView.setDisable(false);
                        commandsListView.setDisable(false);
                        animationView.getRunSimulationButton().setDisable(false);
                        gc.clearRect(0, 0, 512, 512);
                        int xPosition = ((GridPane) animationView.getFloor()).getColumnIndex(tile);
                        int yPosition = ((GridPane) animationView.getFloor()).getRowIndex(tile);
                        RobotDirectionEnum direction = chooseDirection();
                        robotProgrammingView.setUpStartingPosition(xPosition, yPosition, direction);
                    } else {
                        gc.clearRect(0, 0, 512, 512);
                        gc.setFill(Color.BLACK);
                        gc.fillText("Pick robot starting position", 100, 100);
                        gc.setFill(Color.RED);
                        gc.fillText("Starting position must be located on the one of the RED tiles.", 25, 130);
                    }
                });
        }
    }


    private FloorPane createFloor() {
        FloorPane floor = FloorPaneMapper.getFloorStateFromScheme();
        floor.setPadding(new Insets(25, 25, 25, 25));
        return floor;
    }


    private RobotDirectionEnum chooseDirection() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Robot Direction");
        alert.setHeaderText("Choose which direction the robot is facing");
        alert.setContentText("Choose direction");

        ButtonType buttonTypeOne = new ButtonType("North");
        ButtonType buttonTypeTwo = new ButtonType("South");
        ButtonType buttonTypeThree = new ButtonType("East");
        ButtonType buttonTypeFour = new ButtonType("West");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            return RobotDirectionEnum.NORTH;
        } else if (result.get() == buttonTypeTwo) {
            return RobotDirectionEnum.SOUTH;
        } else if (result.get() == buttonTypeThree) {
            return RobotDirectionEnum.EAST;
        } else {
            return RobotDirectionEnum.WEST;
        }
    }
}
