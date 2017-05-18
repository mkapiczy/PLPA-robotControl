package dk.plpa.gui.floorComponents;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile extends StackPane {

    private Rectangle rect;
    private Label sign = new Label("");

    public Tile(double width, double height, Color fillColor){
        super();
        this.rect = new Rectangle(width,height);
        this.rect.setFill(fillColor);
        this.getChildren().addAll(rect, this.sign);
        this.setAlignment(this.sign, Pos.CENTER);
    }

    public Tile(double width, double height, Color fillColor, String sign){
        super();
        this.rect = new Rectangle(width,height);
        this.rect.setFill(fillColor);
        this.sign = new Label(sign);
        this.sign.setFont(Font.font(10));
        this.getChildren().addAll(rect, this.sign);
        this.setAlignment(this.sign, Pos.CENTER);
    }



}
