package dk.plpa;


import dk.plpa.gui.Floor;
import dk.plpa.gui.FloorRow;
import dk.plpa.gui.Tile;
import dk.plpa.scheme.SchemeConfigurer;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.utils.FileReader;
import dk.plpa.utils.FloorMapper;
import gnu.math.IntNum;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class App extends Application {


    public static void main(String[] args) {

        SchemeConfigurer schemeConfigurer = new SchemeConfigurer("src/main/scheme/dk.plpa/factorial.scm");
        schemeConfigurer.configureSchemeEnvironment();

        launch(args);
    }


    @Override
    public void start(Stage theStage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root);
        theStage.setTitle("Robot Control");
        theStage.setScene(scene);


        Floor floor = FloorMapper.readFloorStateFromStringRepresentation(FileReader.readFile("src/main/scheme/dk.plpa/FloorPlan.scm"));

        Canvas canvas = new Canvas(512, 512);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        fillGridPaneWithFloorTiles(gridPane, floor);

        root.getChildren().add(gridPane);
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

    private void fillGridPaneWithFloorTiles(GridPane gridPane, Floor floor) {
        for (int i = 0; i < floor.getRows().size(); i++) {
            FloorRow row = floor.getRows().get(i);
            for (int j = 0; j < row.getNumberOfTiles(); j++) {
                Tile tile = row.getTile(j);
                Rectangle r = new Rectangle();
                r.setFill(tile.getColor());
                r.setStroke(Color.BLACK);
                r.setStrokeWidth(1);
                r.setWidth(20);
                r.setHeight(20);
                gridPane.add(r, j, i);
            }
        }
    }

    private void drawShapes(GraphicsContext gc, Integer i, int positionX, int yPosition) {
        gc.clearRect(0, 0, 512, 512);
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.fillText(i.toString(), positionX, yPosition);
    }
}
