package dk.plpa.gui.elements;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RobotProgrammingCell extends ReorderedCell {
    private HBox hbox = new HBox();
    private Label label = new Label("(empty)");
    private TextField textField;
    private Pane pane = new Pane();
    private Button button = new Button("-");
    private String lastItem;

    public RobotProgrammingCell() {
        super();
        textField = new TextField();
        textField.setMaxWidth(60);
        hbox.getChildren().addAll(label, pane, textField, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(event -> this.getListView().getItems().remove( this.getIndex()));
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        this.setText(null);
        if (empty) {
            lastItem = null;
            this.setGraphic(null);
        } else {
            lastItem = item;
            this.label.setText(item != null ? item : "<null>");
            setGraphic(hbox);
        }
    }

}
