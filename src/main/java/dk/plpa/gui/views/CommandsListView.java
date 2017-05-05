package dk.plpa.gui.views;


import dk.plpa.gui.elements.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class CommandsListView extends AbstractView {

    private ListView<Command> commandsList = new ListView<>();


    public CommandsListView(double width, double height) {
        super(width, height);
    }

    public void setUpViewElements() {
        // TODO elements read from file
        ObservableList<Command> commands = FXCollections.observableArrayList(new Command("MOVE FORWARD"), new Command("TURN RIGHT"),
                new Command("TURN LEFT"), new Command("PICK OBJECT"), new Command("DROP OBJECT"));
        commandsList.setMinWidth(getCanvas().getWidth());
        commandsList.setItems(commands);
        commandsList.setCellFactory(param -> {
            ListCell<Command> cell = new ListCell<Command>() {
                @Override
                public void updateItem(Command item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item != null) {
                        setText(item.getName());
                    }
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
