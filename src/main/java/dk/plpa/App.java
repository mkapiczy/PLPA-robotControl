package dk.plpa;


import dk.plpa.gui.FloorPane;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.FloorPaneMapper;
import gnu.lists.FVector;
import gnu.math.IntNum;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class App extends Application {


    public static void main(String[] args) {
        SchemeConfigurer schemeConfigurer = new SchemeConfigurer();
        schemeConfigurer.configureSchemeEnvironment();

        launch(args);
    }


    @Override
    public void start(Stage theStage) throws Exception {
        Group rootView = new Group();

        Scene scene = new Scene(rootView);
        theStage.setTitle("Robot Control");
        theStage.setScene(scene);

        FloorPane floor = createFloor();

        Group animationView = new Group();
        Canvas animationCanvas = new Canvas(512, 640);
        setUpAnimationView(animationView, floor, animationCanvas);

        Group showProgramCommandsView = new Group();
        Canvas showProgramCommandsCanvas = new Canvas(400,640);
        showProgramCommandsView.getChildren().add(showProgramCommandsCanvas);

        Group programmingView = new Group();
        Canvas programmingCanvas = new Canvas(256,640);
        setUpProgrammingView(programmingView,programmingCanvas);

        SplitPane sidePane = new SplitPane(showProgramCommandsView, programmingView);

        SplitPane mainPane = new SplitPane(animationView, sidePane);
        mainPane.setOrientation(Orientation.HORIZONTAL);

        rootView.getChildren().add(mainPane);

        runAnimation(animationCanvas);

        theStage.show();

    }

    private void runAnimation(Canvas animationCanvas){
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

    private FloorPane createFloor(){
        FloorPane floor = FloorPaneMapper.getFloorStateFromScheme();
        floor.setAlignment(Pos.CENTER);
        floor.setPadding(new Insets(25, 25, 25, 25));
        return floor;
    }

    private void setUpAnimationView(Group animationPartGroup, FloorPane floor, Canvas canvas){
        ObservableList<Node> animationPartChildren = animationPartGroup.getChildren();
        animationPartChildren.add(floor);
        animationPartChildren.add(canvas);
    }

    private void setUpProgrammingView(Group sidePartGroup, Canvas sideCanvas){
        Button buttonForward = new Button("MOVE FORWARD");
        buttonForward.setOnAction(event -> System.out.println("MOVE_FORWARD"));

        Button buttonRight = new Button("TURN_RIGHT");

        Button buttonLeft = new Button("TURN LEFT");
        Button buttonPickObject = new Button("PICK OBJECT");
        Button buttonDropObject = new Button("DROP OBJECT");

        TextField filed = new TextField();
        filed.setLayoutX(350);
        filed.setLayoutY(150);
        buttonForward.setLayoutX(200);
        buttonForward.setLayoutY(50);
        buttonRight.setLayoutX(200);
        buttonRight.setLayoutY(100);
        buttonLeft.setLayoutX(200);
        buttonLeft.setLayoutY(150);
        buttonPickObject.setLayoutX(200);
        buttonPickObject.setLayoutY(200);
        buttonDropObject.setLayoutX(200);
        buttonDropObject.setLayoutY(250);

        sidePartGroup.getChildren().add(sideCanvas);
        sidePartGroup.getChildren().add(buttonForward);
        sidePartGroup.getChildren().add(buttonRight);
        sidePartGroup.getChildren().add(buttonLeft);
        sidePartGroup.getChildren().add(buttonPickObject);
        sidePartGroup.getChildren().add(buttonDropObject);
        sidePartGroup.getChildren().add(filed);
    }


    private void drawShapes(GraphicsContext gc, Integer i, int positionX, int yPosition) {
        gc.clearRect(0, 0, 512, 512);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillText(i.toString(), positionX, yPosition);
    }
}
