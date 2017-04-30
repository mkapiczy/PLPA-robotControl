package dk.plpa.gui.views;


import dk.plpa.gui.elements.RobotProgrammingCell;
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
    private ListView<String> startingPosition = new ListView<>();
    private ListView<String> commandsList = new ListView<>();
    private Button restartProgrammingButton;
    private Button loadProgramToRobotButton;

    public RobotProgrammingView(double width, double height) {
        super(width, height);
    }

    public void setUpStartingPosition(String s){
        ObservableList<String> commands = FXCollections.observableArrayList(s);
        startingPosition.setItems(commands);
    }

    public void setUpViewElements() {
        startingPosition.setMinWidth(this.getCanvas().getWidth());
        startingPosition.setMaxWidth(this.getCanvas().getHeight());
        startingPosition.setMaxHeight(40);

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
                commandsList.getItems().add(db.getString());
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

        });
        vBox.getChildren().addAll(startingPosition, commandsList, restartProgrammingButton, loadProgramToRobotButton);
        vBox.setAlignment(Pos.CENTER);
        VBox.setVgrow(commandsList, Priority.ALWAYS);
        this.getChildren().add(vBox);
    }

    public void restartProgramming(){
        commandsList.getItems().clear();
    }

}
