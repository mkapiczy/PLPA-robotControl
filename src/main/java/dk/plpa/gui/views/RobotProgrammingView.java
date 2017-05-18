package dk.plpa.gui.views;


import dk.plpa.gui.floorComponents.FloorRow;
import dk.plpa.gui.floorComponents.Tile;
import dk.plpa.gui.listViewsComponents.CommandEnum;
import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.gui.listViewsComponents.RobotProgrammingCell;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotState;
import dk.plpa.scheme.LoadCommandsSchemeProcedure;
import dk.plpa.scheme.SchemeProcedure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class RobotProgrammingView extends AbstractView {

    private VBox vBox = new VBox();
    private ListView<String> startingPositionList = new ListView<>();
    private ListView<CommandListItem> commandsList = new ListView<>();
    private RobotState startingPosition;
    private Button restartProgrammingButton;
    private Button loadProgramToRobotButton;
    private AnimationView animationView;
    private CommandsListView commandsListView;

    public RobotProgrammingView(double width, double height) {
        super(width, height);
    }

    public void setUpStartingPosition(int x, int y, RobotDirectionEnum direction) {
        String s = "STARTING POSITION x: " + x + " y: " + y + " direction: " + direction.getValue();
        ObservableList<String> startingPossitionCommand = FXCollections.observableArrayList(s);
        startingPositionList.setItems(startingPossitionCommand);
        startingPosition = new RobotState(x, y, direction, 0, "");
    }

    public void setUpViewElements() {
        startingPositionList.setMinWidth(this.getCanvas().getWidth());
        startingPositionList.setMaxWidth(this.getCanvas().getHeight());
        startingPositionList.setMaxHeight(40);

        commandsList.setCellFactory(param -> new RobotProgrammingCell());
        commandsList.setMinWidth(this.getCanvas().getWidth());
        commandsList.setMaxWidth(this.getCanvas().getWidth());
        commandsList.setOnDragOver(event -> {
            if (event.getGestureSource() != commandsList && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        commandsList.setOnDragEntered(Event::consume);

        commandsList.setOnDragExited(Event::consume);

        commandsList.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                commandsList.getItems().add((new CommandListItem(CommandEnum.getFromCommandName(db.getString()))));
                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        restartProgrammingButton = new Button("Restart programming");
        restartProgrammingButton.setOnMouseClicked(event -> restartProgramming());

        loadProgramToRobotButton = new Button("Load program into robot memory");
        loadProgramToRobotButton.setOnMouseClicked(event -> loadProceduresIntoScheme());
        vBox.getChildren().addAll(startingPositionList, commandsList, restartProgrammingButton, loadProgramToRobotButton);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(commandsList, Priority.ALWAYS);
        this.getChildren().add(vBox);
    }

    private void loadProceduresIntoScheme() {
        LoadCommandsSchemeProcedure.loadProcedures(startingPosition, commandsList.getItems());
        //TODO To remove, used only for testing
        SchemeProcedure displayProcedure = new SchemeProcedure("printGlobalValues");
        displayProcedure.apply0();
    }

    public void restartProgramming() {

        startingPositionList.getItems().clear();
        commandsList.getItems().clear();
        askUserToChooseRobotStartingPosition();
    }

    public void highlightCurrentlyRunningProcedure(int procedureIndex) {
        if (procedureIndex == 0) {
            startingPositionList.getSelectionModel().select(procedureIndex);
        } else {
            startingPositionList.getSelectionModel().clearSelection();
            // -1 because we've got starting position in another list
            final int movementProcedureIndex = procedureIndex - 1;
            if (movementProcedureIndex >= 0 && movementProcedureIndex < this.commandsList.getItems().size()) {
                this.commandsList.scrollTo(movementProcedureIndex);
                this.commandsList.getSelectionModel().select(movementProcedureIndex);
            }
        }
    }

    public void askUserToChooseRobotStartingPosition() {
        disableOtherViewElements();
        animationView.getCanvas().getGraphicsContext2D().clearRect(0, 0, 1024, 1024);
        GraphicsContext gc = this.getCanvas().getGraphicsContext2D();
        gc.fillText("Choose robot starting position", 100, 100);

        setOnClickListenerOnFloorTiles();
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

    private void disableOtherViewElements() {
        this.setDisable(true);
        commandsListView.setDisable(true);
        animationView.getRunSimulationButton().setDisable(true);
        animationView.getCanvas().setMouseTransparent(true);
    }

    private void setOnClickListenerOnFloorTiles() {
        animationView.getFloor().setMouseTransparent(false);
        for (FloorRow row : animationView.getFloor().getRows()) {
            for (Tile tile : row.getTiles())
                tile.setOnMouseClicked(event -> {
                    if (tile.getRect().getFill().equals(Color.RED)) {
                        enableOtherViewElements();
                        int xPosition = ((GridPane) animationView.getFloor()).getColumnIndex(tile);
                        int yPosition = ((GridPane) animationView.getFloor()).getRowIndex(tile);
                        RobotDirectionEnum direction = chooseDirection();
                        this.setUpStartingPosition(xPosition, yPosition, direction);
                    } else {
                        displayWrongTileAlert();
                    }
                });
        }
    }

    //TODO Create some generic Alert Creator component.
    private void displayWrongTileAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wrong tile!");
        alert.setHeaderText("Wrong tile!");
        alert.setContentText("Starting position must be located on one of the RED tiles.");
        alert.showAndWait();
    }


    private void enableOtherViewElements() {
        animationView.getCanvas().setMouseTransparent(false);
        animationView.getFloor().setMouseTransparent(true);
        this.setDisable(false);
        commandsListView.setDisable(false);
        animationView.getRunSimulationButton().setDisable(false);
    }

}
