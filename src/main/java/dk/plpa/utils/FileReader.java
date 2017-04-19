package dk.plpa.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {

    private static final Logger logger = Logger.getLogger(FileReader.class);

    public static String readFile(String path) {
        String fileContent = "";
        try {
            try (BufferedReader br = new BufferedReader(new java.io.FileReader(path))) {
                StringBuilder sb = new StringBuilder();
                br.lines().forEach(line -> {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                });
                fileContent = sb.toString();
            }
        } catch (IOException e) {
            logger.error("Exception while reading file", e);
        }
        return fileContent;
    }
}
