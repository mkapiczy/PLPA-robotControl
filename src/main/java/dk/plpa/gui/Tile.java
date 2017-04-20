package dk.plpa.gui;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile extends Rectangle {

    public Tile(double width, double height, Color fillColor){
        this.setWidth(width);
        this.setHeight(height);
        this.setFill(fillColor);
    }

}
