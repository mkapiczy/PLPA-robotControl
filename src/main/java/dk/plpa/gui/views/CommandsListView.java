package dk.plpa.gui.views;


import dk.plpa.gui.listViewsComponents.CommandListCell;
import dk.plpa.gui.listViewsComponents.CommandListItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class CommandsListView extends AbstractView {

    private ListView<CommandListItem> commandsList = new ListView<>();

    public CommandsListView(double width, double height) {
        super(width, height);
    }

    public void setUpViewElements() {
        // TODO floorComponents read from file
        ObservableList<CommandListItem> commands = FXCollections.observableArrayList(new CommandListItem("MOVE FORWARD"), new CommandListItem("TURN RIGHT"),
                new CommandListItem("TURN LEFT"), new CommandListItem("PICK OBJECT"), new CommandListItem("DROP OBJECT"));
        commandsList.setMinWidth(getCanvas().getWidth());
        commandsList.setItems(commands);

        commandsList.setCellFactory(param -> new CommandListCell());

        this.getChildren().add(commandsList);

    }


}
