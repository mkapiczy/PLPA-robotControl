package dk.plpa.utils;


import dk.plpa.gui.FloorPane;
import dk.plpa.gui.FloorRow;
import dk.plpa.gui.Tile;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class FloorPaneMapper {

    private static final Logger log = Logger.getLogger(FloorPaneMapper.class);

    public static FloorPane readFloorStateFromStringRepresentation(String floorStructure) {
        FloorPane floor = new FloorPane();

        try (BufferedReader br = new BufferedReader(new StringReader(floorStructure))) {
            br.lines().forEach(line -> {
                line = removeUnnecessaryStringElementsFromLine(line);
                if (StringUtils.isNotEmpty(line)) {
                    List<String> tileSignList = Arrays.asList(line.split("'"));
                    floor.addFloorRow(createFloorRowFromTileSignsList(tileSignList));
                }
            });
        } catch (IOException e) {
            log.error("Exception while mapping floor string representation into FloorPane object", e);
        }

        return floor;
    }

    public static FloorPane readFloorStateFromList(List<List<String>> floorStructure) {
        FloorPane floor = new FloorPane();

        floorStructure.forEach(row -> floor.addFloorRow(createFloorRowFromTileSignsList(row)));

        return floor;
    }

    private static String removeUnnecessaryStringElementsFromLine(String line) {
        line = StringUtils.deleteWhitespace(line);
        line = line.replace("(vector", "");
        line = line.replace(")", "");
        return line;
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
