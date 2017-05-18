package dk.plpa.utils;


import dk.plpa.gui.floorComponents.FloorPane;
import dk.plpa.gui.floorComponents.FloorRow;
import dk.plpa.gui.floorComponents.Tile;
import dk.plpa.scheme.SchemeProcedure;
import gnu.lists.FVector;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FloorPaneMapper {

    public static FloorPane createFloor() {
        FloorPane floor = getFloorStateFromScheme();
        floor.setPadding(new Insets(25, 25, 25, 25));
        return floor;
    }

    private static FloorPane getFloorStateFromScheme() {
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
                String tileLabel = mapTileSignToTileLabel(tileSign);
                Tile t;
                if (!tileLabel.equals("")) {
                    t = new Tile(20, 20, mapTileSignToTheColor(tileSign), tileLabel);
                } else {
                    t = new Tile(20, 20, mapTileSignToTheColor(tileSign));
                }
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
        } else if (tileSign.equals("i")) {
            return Color.CHOCOLATE;
        } else if (tileSign.equals("o")) {
            return Color.ORANGE;
        } else if (tileSign.equals("*")) {
            return Color.BURLYWOOD;
        } else {
            return Color.YELLOW;
        }
    }

    private static String mapTileSignToTileLabel(String tileSign) {
        if (tileSign.equals("i") || tileSign.equals("o")) {
            return tileSign;
        } else if (tileSign.equals("*")) {
            return "i/o";
        } else {
            return "";
        }
    }
}
