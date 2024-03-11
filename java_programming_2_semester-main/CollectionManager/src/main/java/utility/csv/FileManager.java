package utility.csv;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileManager interface provides methods for reading from and writing to CSV files.
 */
public interface FileManager {

    /**
     * Reads data from the specified CSV file.
     *
     * @param pathToFile the path to the CSV file to be read
     * @return an ArrayList containing the lines read from the CSV file
     * @throws IOException if an I/O error occurs while reading from the file
     */
    ArrayList<String> readFromFile (String pathToFile) throws IOException;

    /**
     * Writes data to the specified CSV file.
     *
     * @param pathToFile the path to the CSV file to be written
     * @param header     an array of strings representing the header row of the CSV file
     * @param records    a list of strings representing the data records to be written
     * @throws IOException if an I/O error occurs while writing to the file
     */
    void writeToFile (String pathToFile, String[] header, List<String> records) throws IOException;
}
