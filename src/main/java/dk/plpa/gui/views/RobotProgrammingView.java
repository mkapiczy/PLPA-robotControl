package dk.plpa.gui.views;


import dk.plpa.gui.elements.RobotProgrammingCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class RobotProgrammingView extends AbstractView {

    private ListView<String> commandsList = new ListView<>();

    public RobotProgrammingView(double width, double height) {
        super(width, height);
    }

    public void setUpViewElements() {
        // TODO Remove this elements only from the CommandsListView
        ObservableList<String> items = FXCollections.observableArrayList ("MOVE FORWARD", "TURN RIGHT", "TURN LEFT", "PICK OBJECT", "DROP OBJECT");
        commandsList.setItems(items);

        commandsList.setCellFactory(param -> new RobotProgrammingCell());
        commandsList.setMinWidth(this.getCanvas().getWidth());
        commandsList.setMaxWidth(this.getCanvas().getWidth());

        commandsList.setOnDragOver(event -> {
            if (event.getGestureSource() != commandsList && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        commandsList.setOnDragEntered(event -> event.consume());

        commandsList.setOnDragExited(event -> event.consume());

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

        this.getChildren().add(commandsList);
    }

}
