package file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
/**

 The CSVWriter class provides methods for writing data to a CSV file.
 It supports writing a header line followed by a list of records.
 */
public class CSVWriter {

    /**
     * Writes data to a CSV file with the specified path.
     *
     * @param pathToFile The path to the CSV file.
     * @param header The header line to be written at the beginning of the file.
     * @param records The list of records to be written to the file.
     * @throws IllegalArgumentException if there is an I/O error while writing the file.
     */
    public static void writeToFile(String pathToFile, String[] header, List<String> records) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(pathToFile))) {
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
}
