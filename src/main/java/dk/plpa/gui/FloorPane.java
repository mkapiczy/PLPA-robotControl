package dk.plpa.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FloorPane extends GridPane {

    private List<FloorRow> rows = new ArrayList<>();

    public void addFloorRow(FloorRow row) {
        rows.add(row);
        fillGridPaneWithLastRowTiles();
    }

    private void fillGridPaneWithLastRowTiles() {
        int amountOfFloorRows = this.getRows().size();
        int indexOfRowToBeAdded = amountOfFloorRows - 1;
        FloorRow row = this.getRows().get(indexOfRowToBeAdded);
        for (int j = 0; j < row.getNumberOfTiles(); j++) {
            Tile tile = row.getTile(j);
            Rectangle r = new Rectangle(20, 20, tile.getColor());
            r.setStroke(Color.BLACK);
            r.setStrokeWidth(1);
            this.add(r, j, indexOfRowToBeAdded);
        }
    }
}

