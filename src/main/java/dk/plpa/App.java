package dk.plpa;


import dk.plpa.gui.views.AnimationView;
import dk.plpa.gui.elements.FloorPane;
import dk.plpa.gui.views.CommandsListView;
import dk.plpa.gui.views.RobotProgrammingView;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.FloorPaneMapper;
import gnu.math.IntNum;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application {


    public static void main(String[] args) {
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();

        launch(args);
    }


    @Override
    public void start(Stage theStage) throws Exception {
        AnimationView animationView = new AnimationView(512, 640);
        RobotProgrammingView robotProgrammingView = new RobotProgrammingView(400, 640);
        CommandsListView commandsListView = new CommandsListView(256, 640);

        setUpViews(theStage, animationView, robotProgrammingView, commandsListView);
        robotProgrammingView.setUpViewElements();
        runAnimation(animationView.getCanvas());


        theStage.show();
    }

    private void setUpViews(Stage theStage, AnimationView animationView, RobotProgrammingView robotProgrammingView, CommandsListView commandsListView) {
        Group rootView = new Group();

        Scene scene = new Scene(rootView);
        theStage.setTitle("Robot Control");
        theStage.setScene(scene);

        FloorPane floor = createFloor();

        animationView.setBackground(floor);

        commandsListView.setUpViewElements();

        SplitPane sidePane = new SplitPane(robotProgrammingView, commandsListView);

        SplitPane mainPane = new SplitPane(animationView, sidePane);

        rootView.getChildren().add(mainPane);
    }

    private void runAnimation(Canvas animationCanvas) {
        GraphicsContext gc = animationCanvas.getGraphicsContext2D();

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

    private FloorPane createFloor() {
        FloorPane floor = FloorPaneMapper.getFloorStateFromScheme();
        floor.setAlignment(Pos.CENTER);
        floor.setPadding(new Insets(25, 25, 25, 25));
        return floor;
    }


    private void drawShapes(GraphicsContext gc, Integer i, int positionX, int yPosition) {
        gc.clearRect(0, 0, 512, 512);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillText(i.toString(), positionX, yPosition);
    }
}
