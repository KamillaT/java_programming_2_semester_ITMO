package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {
    private static String pathToFile;
    private static boolean flag = false;

    public static void setPathToFile (String fileName) {
        CSVReader.pathToFile = fileName;
    }

    /**
     * Reads the contents of a CSV file and returns them as a list of lines.
     *
     * @return A list of lines read from the CSV file.
     */
    public static ArrayList<String> readFromFile() {
        ArrayList<String> lineList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flag = true;
                lineList.add(line.trim());
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("CSV format violation: " + exception.getMessage());
        }
        return lineList;
    }

    public static boolean getFlag() {
        return flag;
    }
}
