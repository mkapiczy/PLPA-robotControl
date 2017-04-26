package dk.plpa.gui.views;


import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RobotProgrammingView extends AbstractView {

    public RobotProgrammingView(double width, double height) {
        super(width, height);
    }

    public void setUpProgrammingViewElements(){
        Button buttonForward = new Button("MOVE FORWARD");
        buttonForward.setOnAction(event -> System.out.println("MOVE_FORWARD"));

        Button buttonRight = new Button("TURN RIGHT");
        Button buttonLeft = new Button("TURN LEFT");
        Button buttonPickObject = new Button("PICK OBJECT");
        Button buttonDropObject = new Button("DROP OBJECT");

        TextField textField= new TextField();
        textField.setLayoutX(350);
        textField.setLayoutY(150);

        buttonForward.setLayoutX(200);
        buttonForward.setLayoutY(50);

        buttonRight.setLayoutX(200);
        buttonRight.setLayoutY(100);

        buttonLeft.setLayoutX(200);
        buttonLeft.setLayoutY(150);

        buttonPickObject.setLayoutX(200);
        buttonPickObject.setLayoutY(200);

        buttonDropObject.setLayoutX(200);
        buttonDropObject.setLayoutY(250);

        ObservableList<Node> sidePartViewChildren = this.getChildren();

        sidePartViewChildren.add(buttonForward);
        sidePartViewChildren.add(buttonRight);
        sidePartViewChildren.add(buttonLeft);
        sidePartViewChildren.add(buttonPickObject);
        sidePartViewChildren.add(buttonDropObject);
        sidePartViewChildren.add(textField);
    }

}
