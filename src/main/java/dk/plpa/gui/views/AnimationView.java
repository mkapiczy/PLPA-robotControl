package dk.plpa.gui.views;


import dk.plpa.gui.elements.FloorPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AnimationView extends AbstractView {

    private FloorPane floor;
    private Button runSimulationButton;
    private VBox vBox = new VBox();

    public AnimationView(double width, double height) {
        super(width, height);
    }

    @Override
    public void setBackground(Node background) {
        super.setBackground(background);
        if (background instanceof FloorPane) {
            this.floor = (FloorPane) background;
        }
    }

    public void setUpViewElements(){
        runSimulationButton = new Button("Run simulation");
        runSimulationButton.setPadding(new Insets(25, 25, 25, 25));
        Group group = new Group();
        group.getChildren().addAll(floor, getCanvas());
        vBox.getChildren().addAll(group, runSimulationButton);
        vBox.setAlignment(Pos.CENTER);
        this.getChildren().clear();
        this.getChildren().add(vBox);
    }
}
