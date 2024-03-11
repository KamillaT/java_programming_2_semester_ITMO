package utility.csv;

import exceptions.WrongInputInScriptException;
import utility.ConsolePrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**

 The CSVReader class provides methods for reading the contents of a CSV file.
 It supports reading the file and returning its contents as a list of lines.
 */
public class CSVReader {
    private static boolean flag = false;

    /**
     * Reads the contents of a CSV file and returns them as a list of lines.
     *
     * @param pathToFile The path to the CSV file.
     * @return A list of lines read from the CSV file.
     */
    public static ArrayList<String> readFromFile(String pathToFile) {
        ArrayList<String> lineList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flag = true;
                lineList.add(line.trim());
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("CSV format violation: " + exception.getMessage());
        } catch (NoSuchElementException exception) {
            throw new NoSuchElementException(exception.getMessage());
        }
        return lineList;
    }

    /**
     * Returns the value of the flag.
     *
     * @return The value of the flag.
     */
    public static boolean getFlag() {
        return flag;
    }
}
