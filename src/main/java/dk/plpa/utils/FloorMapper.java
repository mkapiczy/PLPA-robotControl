package dk.plpa.utils;


import dk.plpa.gui.Floor;
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

public class FloorMapper {

    private static final Logger log = Logger.getLogger(FloorMapper.class);

    public static Floor readFloorStateFromStringRepresentation(String floorStructure) {
        Floor floor = new Floor();

        try (BufferedReader br = new BufferedReader(new StringReader(floorStructure))) {
            br.lines().forEach(line -> {
                line = removeUnnecessaryStringElementsFromLine(line);
                if (StringUtils.isNotEmpty(line)) {
                    List<String> tileSignList = Arrays.asList(line.split("'"));
                    floor.addRow(createFloorRowFromTileSignsList(tileSignList));
                }
            });
        } catch (IOException e) {
            log.error("Exception while mapping floor string representation into Floor object", e);
        }

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
                Tile t = new Tile(mapTileSignToTheColor(tileSign));
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
