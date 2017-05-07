package dk.plpa;


import dk.plpa.gui.Dimensions;
import dk.plpa.gui.elements.Direction;
import dk.plpa.gui.elements.FloorPane;
import dk.plpa.gui.elements.FloorRow;
import dk.plpa.gui.elements.Tile;
import dk.plpa.gui.views.AnimationView;
import dk.plpa.gui.views.CommandsListView;
import dk.plpa.gui.views.RobotProgrammingView;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.FloorPaneMapper;
import gnu.math.IntNum;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.util.Optional;


public class App extends Application {

    private Stage theStage;
    private AnimationView animationView;
    private RobotProgrammingView robotProgrammingView;
    private CommandsListView commandsListView;
    private Image robotImg;

    public static void main(String[] args) {
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();

        launch(args);
    }


    @Override
    public void start(Stage theStage) throws Exception {
        this.animationView = new AnimationView(Dimensions.ANIMATION_VIEW_WIDTH, Dimensions.VIEWS_HEIGHT);
        this.robotProgrammingView = new RobotProgrammingView(Dimensions.ROBOT_PROGRAMMING_VIEW_WIDTH, Dimensions.VIEWS_HEIGHT);
        this.commandsListView = new CommandsListView(Dimensions.COMMANDS_LIST_VIEW_WIDTH, Dimensions.VIEWS_HEIGHT);
        this.theStage = theStage;
        robotImg = new Image("File:" + System.getProperty("user.dir") +"\\src\\main\\java\\dk\\plpa\\gui\\robot.png");

        setUpViews();

        askUserToChooseRobotStartingPosition();

        theStage.show();
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
                        int xCoord = ((GridPane) animationView.getFloor()).getRowIndex(tile);
                        int yCoord = ((GridPane) animationView.getFloor()).getColumnIndex(tile);
                        Direction direction = chooseDirection();
                        robotProgrammingView.setUpStartingPosition(xCoord, yCoord, direction);
                        tile.setFill(createRobotImagePattern(direction,Color.RED));

                        runAnimation();
                    } else {
                        gc.clearRect(0, 0, 512, 512);
                        gc.setFill(Color.BLACK);
                        gc.fillText("Pick robot starting position", 100, 100);
                        gc.setFill(Color.RED);
                        gc.fillText("Starting position must be located on the one of the RED tiles.", 70, 130);
                    }
                });
        }
    }

    private ImagePattern createRobotImagePattern(Direction direction, Color backgroundColor) {
        ImageView pic = new ImageView(robotImg);
        pic.setRotate(direction.getImageRotation());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(backgroundColor);
        Image img = pic.snapshot(params, null);
        return new ImagePattern(img);
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

    private FloorPane createFloor() {
        FloorPane floor = FloorPaneMapper.getFloorStateFromScheme();
        floor.setPadding(new Insets(25, 25, 25, 25));
        return floor;
    }

    private void runAnimation() {
        GraphicsContext gc = animationView.getCanvas().getGraphicsContext2D();

        new AnimationTimer() {
            private int currentState = 0;
            private long lastUpdate = 0;
            private int xPosition = 10;
            private int yPosition = 250;

            public void handle(long currentNanoTime) {
                if (currentNanoTime - lastUpdate >= 500000000) {
                    SchemeProcedure incrementProcedure = new SchemeProcedure("increment");
                    currentState = ((IntNum) incrementProcedure.apply1(currentState)).intValue();
                    System.out.println(currentState);

                    drawShapes(gc, currentState, xPosition, yPosition);
                    xPosition += 25;
                    yPosition += 10;

                    if (currentState == 20) {
                        this.stop();
                    }
                    lastUpdate = currentNanoTime;
                }

            }
        }.start();
    }


    private void drawShapes(GraphicsContext gc, Integer i, int positionX, int yPosition) {
        gc.clearRect(0, 0, 512, 512);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillText(i.toString(), positionX, yPosition);
    }

    private Direction chooseDirection() {
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

        if (result.get() == buttonTypeOne){
            return Direction.NORTH;
        } else if (result.get() == buttonTypeTwo) {
            return Direction.SOUTH;
        } else if (result.get() == buttonTypeThree) {
            return Direction.EAST;
        } else {
           return Direction.WEST;
        }
    }
}
