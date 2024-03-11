package utility.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The CSVManager class implements the FileManager interface and provides methods for reading from and writing to CSV files.
 */
public class CSVManager implements FileManager {
    private static boolean flag = false;

    /**
     * Reads the contents of a CSV file and returns them as a list of lines.
     *
     * @param pathToFile The path to the CSV file.
     * @return A list of lines read from the CSV file.
     */
    @Override
    public ArrayList<String> readFromFile(String pathToFile) {
        ArrayList<String> lineList = new ArrayList<>();
        try {
            CSVParser parser = CSVFormat.DEFAULT.parse(new BufferedReader(new FileReader(pathToFile)));
            List<CSVRecord> records = parser.getRecords();
            for (CSVRecord record : records) {
                flag = true;
                StringBuilder stringBuilder = new StringBuilder();
                for (String element : record) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(element.trim());
                }
                lineList.add(stringBuilder.toString());
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("CSV format violation: " + exception.getMessage());
        }

        return lineList;
    }

    /**
     * Writes the specified header and records to a CSV file.
     *
     * @param pathToFile The path to the CSV file.
     * @param header     The header line to be written to the CSV file.
     * @param records    The list of records to be written to the CSV file.
     * @throws IllegalArgumentException If there is an error writing the CSV file.
     */
    @Override
    public void writeToFile(String pathToFile, String[] header, List<String> records) {
        try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(pathToFile)))) {
            // Write header
            for (int i = 0; i < header.length; i++) {
                writer.print(header[i]);
                if (i < header.length - 1) {
                    writer.print(",");
                }
            }
            writer.println(); // Move to next line after writing header

            // Write records
            for (String record : records) {
                String[] fields = record.split(",");
                for (int i = 0; i < fields.length; i++) {
                    writer.print(fields[i]);
                    if (i < fields.length - 1) {
                        writer.print(",");
                    }
                }
                writer.println(); // Move to next line after writing record
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("Error writing CSV file: " + exception.getMessage());
        }
    }

    /**
     * Retrieves the flag indicating whether the CSV file was successfully read.
     *
     * @return The flag indicating whether the CSV file was successfully read.
     */
    public static boolean getFlag() {
        return flag;
    }
}