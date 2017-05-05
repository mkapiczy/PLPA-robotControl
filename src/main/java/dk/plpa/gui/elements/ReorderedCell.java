package dk.plpa.gui.elements;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ReorderedCell extends ListCell<Command> {

    public ReorderedCell() {
        ListCell thisCell = this;
        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(getItem().getName());
            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (!this.getListView().getItems().contains(event.getGestureSource())) {
                event.acceptTransferModes(TransferMode.ANY);
            } else if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != thisCell &&
                    event.getDragboard().hasString()) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }
            Dragboard db = event.getDragboard();
            boolean success = false;

            Object gestureSource = event.getGestureSource();
            Object gestureTarget = event.getGestureTarget();

            if (!(gestureSource instanceof ReorderedCell)) {
                if (db.hasString()) {
                    this.getListView().getItems().add(((ReorderedCell) event.getGestureTarget()).getIndex(), new Command(db.getString()));
                    success = true;
                }
            } else if (gestureSource != thisCell) {
                if (db.hasString()) {

                    RobotProgrammingCell gestureSourceCell = (RobotProgrammingCell) gestureSource;
                    RobotProgrammingCell gestureTargetCell = (RobotProgrammingCell) gestureTarget;

                    int sourceIdx = gestureSourceCell.getIndex();
                    int targetIndex = gestureTargetCell.getIndex();
                    ObservableList<Command> items = this.getListView().getItems();

                    Command sourceTempContent = items.get(sourceIdx);
                    items.set(sourceIdx, items.get(targetIndex));
                    items.set(targetIndex, sourceTempContent);

                    success = true;
                }
            }
            event.setDropCompleted(success);

            event.consume();
        });


        setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(Command item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            setText(item.getName());
        }
    }
}