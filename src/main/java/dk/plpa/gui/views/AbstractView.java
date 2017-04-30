package dk.plpa.gui.views;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractView extends Group{

    private Canvas canvas;

    public AbstractView(double width, double height){
        super();
        this.canvas = new Canvas(width, height);
        this.getChildren().add(this.canvas);
    }

    public void setBackground(Node background) {
        if(this.getChildren().contains(this.canvas)){
            this.getChildren().remove(this.canvas);
        }

        this.getChildren().add(background);
        this.getChildren().add(canvas);
    }


}
