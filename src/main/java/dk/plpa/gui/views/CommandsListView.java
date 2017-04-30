package dk.plpa.gui.views;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class CommandsListView extends AbstractView {

    private ListView<String> commandsList = new ListView<>();


    public CommandsListView(double width, double height) {
        super(width, height);
    }

    public void setUpViewElements() {
        // TODO elements read from file
        ObservableList<String> commands = FXCollections.observableArrayList("MOVE FORWARD", "TURN RIGHT",
                "TURN LEFT", "PICK OBJECT", "DROP OBJECT");
        commandsList.setMinWidth(getCanvas().getWidth());
        commandsList.setItems(commands);
        commandsList.setCellFactory(param -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                }
            };

            cell.setOnDragDetected(dragEvent -> {
                Dragboard db = cell.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString(cell.getText());
                db.setContent(content);
                db.setDragView(cell.snapshot(null, new WritableImage((int) cell.getWidth(), (int) cell.getHeight())));
                dragEvent.consume();
            });

            cell.setOnDragDone(event -> event.consume());

            return cell;
        });

        this.getChildren().add(commandsList);

    }

}
