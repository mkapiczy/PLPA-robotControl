package dk.plpa.gui.floorComponents;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FloorRow {

    List<Tile> tiles = new ArrayList<>();

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public Tile getTile(int i) {
        return tiles.get(i);
    }

    public int getNumberOfTiles() {
        return this.tiles.size();
    }
}
