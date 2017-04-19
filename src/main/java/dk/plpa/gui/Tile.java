package dk.plpa.gui;


import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile extends Region {

    private Color color;

    public Tile(Color color) {
        this.color = color;
    }


}
