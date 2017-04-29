package dk.plpa;

import dk.plpa.gui.elements.ReorderedCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListOrganizer extends Application {




    private static final ObservableList<String> birds = FXCollections.observableArrayList(
            "-black",
            "-blue",
            "-red",
            "-red-2",
            "-yellow",
            "s-green",
            "s-green-2"
    );


    @Override
    public void start(Stage stage) throws Exception {


        ListView<String> birdList = new ListView<>(birds);
        birdList.setCellFactory(param -> new ReorderedCell());
        birdList.setPrefWidth(180);

        VBox layout = new VBox(birdList);
        layout.setPadding(new Insets(10));

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) {
        launch(ListOrganizer.class);
    }



}