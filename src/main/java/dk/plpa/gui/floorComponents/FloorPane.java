package dk.plpa.gui.floorComponents;

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
            tile.getRect().setStroke(Color.BLACK);
            tile.getRect().setStrokeWidth(1);
            this.add(tile, tileIndex, indexOfLastRow);
        }
    }

    public Tile getTileByPosition(int x, int y) {
        Tile tile = null;
        if (y >= 0 && y <= this.rows.size()) {
            FloorRow row = this.rows.get(y);
            tile = row.getTile(x);
        }
        return tile;
    }


}

