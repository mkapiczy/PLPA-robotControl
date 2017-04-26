package dk.plpa.utils;


import dk.plpa.gui.FloorPane;
import dk.plpa.gui.FloorRow;
import dk.plpa.gui.Tile;
import dk.plpa.scheme.SchemeProcedure;
import gnu.lists.FVector;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FloorPaneMapper {

    public static FloorPane getFloorStateFromScheme() {
        FloorPane floor = new FloorPane();

        SchemeProcedure floorProc = new SchemeProcedure("getFactoryFloor");
        Object[] floorArrayRepresentation = ((FVector) floorProc.apply0()).toArray();
        List<List<String>> floorStructure = convertFloorToListRepresentaton(floorArrayRepresentation);
        floorStructure.forEach(row -> floor.addFloorRow(createFloorRowFromTileSignsList(row)));

        return floor;
    }


    private static List<List<String>> convertFloorToListRepresentaton(Object[] floorArrayRepresentation) {
        List<List<String>> floorListRepresentation = new ArrayList<>();

        for (Object floorObj : floorArrayRepresentation) {
            List<String> singleRowElements = ((List<Object>) floorObj).stream().map(Object::toString).collect(Collectors.toList());
            floorListRepresentation.add(singleRowElements);
        }

        return floorListRepresentation;
    }


    private static FloorRow createFloorRowFromTileSignsList(List<String> tileSignList) {
        FloorRow row = new FloorRow();
        for (String tileSign : tileSignList) {
            if (!tileSign.equals("")) {
                Tile t = new Tile(20, 20, mapTileSignToTheColor(tileSign));
                row.addTile(t);
            }
        }
        return row;
    }

    /*
    To be adjusted later for all possible signs without else yellow
     */
    private static Color mapTileSignToTheColor(String tileSign) {
        if (tileSign.equals("-")) {
            return Color.WHITE;
        } else if (tileSign.equals("A")) {
            return Color.GREEN;
        } else if (tileSign.equals("P")) {
            return Color.RED;
        } else if (tileSign.equals("S")) {
            return Color.GREY;
        } else {
            return Color.YELLOW;
        }
    }
}
