package utility.csv;

import data.*;
import utility.CollectionManager;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for loading and saving collections to CSV files.
 */
public class CSVProcess {
    private static String pathToFile;
    private static PriorityQueue<Product> products;

    /**
     * Retrieves the path to the CSV file.
     *
     * @return The path to the CSV file.
     */
    public static String getPathToFile() {
        return pathToFile;
    }

    /**
     * Sets the path to the CSV file.
     *
     * @param pathToFile The path to the CSV file.
     */
    public static void setPathToFile(String pathToFile) {
        CSVProcess.pathToFile = pathToFile;
    }

    /**
     * Sets the collection of products.
     *
     * @param products The collection of products.
     */
    public static void setCollection(PriorityQueue<Product> products) {
        CSVProcess.products = products;
    }

    /**
     * Loads the collection from the specified CSV file.
     *
     * @param fileName The name of the CSV file to load the collection from.
     * @throws IllegalArgumentException If there is an error in the CSV format.
     */
    public static PriorityQueue<Product> loadCollection(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("File name has not been provided!");
        } else {
            try {
                List<String> parsedCSVFile = CSVReader.readFromFile(fileName);
                CollectionManager.initializationCollection();
                PriorityQueue<Product> products = CollectionManager.getCollection();
                boolean isFirstLine = true;
                for (String line : parsedCSVFile) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] elements = line.split(",");
                    int id = Integer.parseInt(elements[0]);
                    String name = elements[1];
                    Double x = Double.valueOf(elements[2]);
                    Double y = Double.valueOf(elements[3]);
                    Coordinates coordinates = new Coordinates(x,y);
                    Float price = Float.valueOf(elements[4]);
                    UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(elements[5]);
                    long productId = Long.parseLong(elements[6]);
                    String productName = elements[7];
                    Double annualTurnover = Double.valueOf(elements[8]);
                    OrganizationType productType = OrganizationType.valueOf(elements[9]);
                    String street = elements[10];
                    String zipCode = elements[11];
                    Address address = new Address(street, zipCode);
                    Organization organization = new Organization(productId, productName, annualTurnover,
                            productType, address);
                    Product product = new Product(id, name, coordinates, price, unitOfMeasure, organization);
                    products.add(product);
                }
                CSVProcess.products = products;
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("CSV Format Violation!: " + exception.getMessage());
            }
        }
        return products;
    }

    /**
     * Writes the collection to the CSV file.
     *
     * @throws IllegalArgumentException If there is an error in the CSV format.
     */
    public static void writeCollection() {
        String[] headers = {"id", "name", "x", "y", "annual turnover",
                "full name", "employees count", "type", "postal address"};
        List<String> records = new ArrayList<>();

        for (Product product : CollectionManager.getCollection()) {
            String[] fields = {
                    String.valueOf(product.getId()),
                    product.getName(),
                    String.valueOf(product.getCoordinates().getX()),
                    String.valueOf(product.getCoordinates().getY()),
                    String.valueOf(product.getPrice()),
                    String.valueOf(product.getUnitOfMeasure()),
                    String.valueOf(product.getOrganization().getId()),
                    product.getOrganization().getName(),
                    String.valueOf(product.getOrganization().getAnnualTurnover()),
                    String.valueOf(product.getOrganization().getType()),
                    product.getOrganization().getPostalAddress().getStreet(),
                    product.getOrganization().getPostalAddress().getZipCode(),
            };
            records.add(String.join(",", fields));
        }

        CSVWriter.writeToFile(pathToFile, headers, records);
    }
}
