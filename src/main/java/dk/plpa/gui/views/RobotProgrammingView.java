package dk.plpa.gui.views;


import dk.plpa.gui.listViewsComponents.CommandEnum;
import dk.plpa.gui.listViewsComponents.CommandListItem;
import dk.plpa.gui.listViewsComponents.RobotProgrammingCell;
import dk.plpa.robot.RobotDirectionEnum;
import dk.plpa.robot.RobotPosition;
import dk.plpa.scheme.SchemeProcedure;
import dk.plpa.scheme.SchemeTypesMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RobotProgrammingView extends AbstractView {

    private VBox vBox = new VBox();
    private ListView<String> startingPositionLabel = new ListView<>();
    private RobotPosition startingPosition;
    private ListView<CommandListItem> commandsList = new ListView<>();
    private Button restartProgrammingButton;
    private Button loadProgramToRobotButton;

    public RobotProgrammingView(double width, double height) {
        super(width, height);
    }

    public void setUpStartingPosition(int x, int y, RobotDirectionEnum direction) {
        String s = "STARTING POSITION x: " + x + " y: " + y + " direction: " + direction.getValue();
        ObservableList<String> commands = FXCollections.observableArrayList(s);
        startingPositionLabel.setItems(commands);
        startingPosition = new RobotPosition(x, y, direction);
    }

    public void setUpViewElements() {
        startingPositionLabel.setMinWidth(this.getCanvas().getWidth());
        startingPositionLabel.setMaxWidth(this.getCanvas().getHeight());
        startingPositionLabel.setMaxHeight(40);

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
        restartProgrammingButton.setOnMouseClicked(event -> {
            restartProgramming();
        });

        loadProgramToRobotButton = new Button("Load program into robot memory");
        loadProgramToRobotButton.setOnMouseClicked(event -> {
            loadProceduresIntoScheme();
        });
        vBox.getChildren().addAll(startingPositionLabel, commandsList, restartProgrammingButton, loadProgramToRobotButton);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(commandsList, Priority.ALWAYS);
        this.getChildren().add(vBox);
    }

    private void loadProceduresIntoScheme() {
        SchemeProcedure loadProcedure = new SchemeProcedure("loadCommands");

        String s = SchemeTypesMapper.createLoadCommandSchemeArgument(startingPosition, commandsList.getItems());

        loadProcedure.apply1(s);

        SchemeProcedure displayProcedure = new SchemeProcedure("printGlobalValues");
        displayProcedure.apply0();
    }

    public void restartProgramming() {
        commandsList.getItems().clear();
    }

}
