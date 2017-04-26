package dk.plpa;


import dk.plpa.gui.FloorPane;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.FloorPaneMapper;
import gnu.lists.FVector;
import gnu.math.IntNum;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
        Group root = new Group();
        Scene scene = new Scene(root);
        theStage.setTitle("Robot Control");
        theStage.setScene(scene);

        Canvas canvas = new Canvas(512, 512);

        SchemeProcedure floorProc = new SchemeProcedure("getFactoryFloor");
        Object[] floorObj =((FVector) floorProc.apply0()).toArray();

        List<List<String>> floorArr = convertFloorToLists(floorObj);

        //FloorPane floor = FloorPaneMapper.readFloorStateFromStringRepresentation(FileReader.readFile("src/main/scheme/dk.plpa/FloorPlan.scm"));
        FloorPane floor = FloorPaneMapper.readFloorStateFromList(floorArr);
        floor.setAlignment(Pos.CENTER);
        floor.setPadding(new Insets(25, 25, 25, 25));

        root.getChildren().add(floor);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

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

        theStage.show();

    }

    private List<List<String>> convertFloorToLists(Object[] floorObj) {
        List<List<String>> floorArr = new ArrayList<>();
        for (Object aFloorObj : floorObj) {
            List<Object> rowObj = (List<Object>) aFloorObj;
            List<String> stringList = rowObj.stream().map(Object::toString).collect(Collectors.toList());
            floorArr.add(stringList);
        }
        return floorArr;
    }


    private void drawShapes(GraphicsContext gc, Integer i, int positionX, int yPosition) {
        gc.clearRect(0, 0, 512, 512);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillText(i.toString(), positionX, yPosition);
    }
}
