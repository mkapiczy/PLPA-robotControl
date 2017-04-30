package dk.plpa.gui.elements;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FloorPane extends GridPane {

    @Setter(AccessLevel.NONE)
    private List<FloorRow> rows = new ArrayList<>();


    public void addFloorRow(FloorRow row) {
        rows.add(row);
        addLastRowTilesToTheGridPane();
    }

    private void addLastRowTilesToTheGridPane() {
        int amountOfFloorRows = this.getRows().size();
        int indexOfLastRow = amountOfFloorRows - 1;
        FloorRow row = this.getRows().get(indexOfLastRow);
        for (int tileIndex = 0; tileIndex < row.getNumberOfTiles(); tileIndex++) {
            Tile tile = row.getTile(tileIndex);
            tile.setStroke(Color.BLACK);
            tile.setStrokeWidth(1);
            this.add(tile, tileIndex, indexOfLastRow);
        }
    }


}

