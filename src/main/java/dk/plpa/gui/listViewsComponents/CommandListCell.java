package dk.plpa.gui.listViewsComponents;

import javafx.scene.control.ListCell;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;


public class CommandListCell extends ListCell<CommandListItem> {

    public CommandListCell() {
        this.setOnDragDetected(dragEvent -> {
            Dragboard db = this.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(this.getText());
            db.setContent(content);
            db.setDragView(this.snapshot(null, new WritableImage((int) this.getWidth(), (int) this.getHeight())));
            dragEvent.consume();
        });

        this.setOnDragDone(event -> event.consume());

    }

    @Override
    public void updateItem(CommandListItem item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getCommandName());
        }
    }
}
