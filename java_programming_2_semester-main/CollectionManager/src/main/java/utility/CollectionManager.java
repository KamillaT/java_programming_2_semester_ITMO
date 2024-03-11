package utility;

import utility.csv.CSVProcess;

import java.time.LocalDateTime;
import java.util.*;
import data.*;

import exceptions.EmptyCollectionException;

public class CollectionManager {
    private static boolean addIfMinFlag;
    public static Stack<String> historyCommandList = new Stack<>() ;
    private static List<Product> clearList = new ArrayList<>();
    public static Stack<Product> productStack = new Stack<>();

    private static PriorityQueue<Product> priorityQueue;
    private static LocalDateTime initializationDate;

    private static Comparator<Product> descendingOrderComparator = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            if (p1.getPrice() < p2.getPrice())
                return 1;
            if (p1.getPrice() == p2.getPrice())
                return 0;
            return -1;
        }
    };

    private static Comparator<Float> descendingOrderComparatorFloat = new Comparator<Float>() {
        @Override
        public int compare(Float p1, Float p2) {
            if (p1 < p2)
                return 1;
            if (p1 == p2)
                return 0;
            return -1;
        }
    };

    /**
     * Initializes the collection if it is null.
     */
    public static void initializationCollection() {
            priorityQueue = new PriorityQueue<>();
            initializationDate = LocalDateTime.now();
    }

    /**
     * Retrieves the collection of products.
     *
     * @return The collection of products.
     */
    public static PriorityQueue<Product> getCollection() {
        return priorityQueue;
    }

    /**
     * Retrieves an product from the collection based on its ID.
     *
     * @param ID The ID of the product.
     * @return The product with the specified ID, or null if not found.
     */
    public static Product getProductByID (int ID) {
        for(Product product : priorityQueue) {
            int id = product.getId().intValue();
            if (id == ID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Checks if an product with the specified ID exists in the collection.
     *
     * @param ID The ID of the product.
     * @return true if an product with the specified ID exists, false otherwise.
     */
    public static boolean idExistence(int ID) {
        for (Product product : priorityQueue) {
            if (product.getId().intValue() == ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints information about the collection, such as its type, initialization date, and number of elements.
     */
    public static void information() {
        ConsolePrinter.printInformation("Type of collection: " + priorityQueue.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + priorityQueue.size() + ".");

    }

    /**
     * Prints detailed information about all products in the collection.
     * Throws an EmptyCollectionException if the collection is empty.
     */
    public static void fullInformation() {
        try{
            if (priorityQueue.isEmpty()) throw new EmptyCollectionException();
            for (Product product : priorityQueue) {
                ConsolePrinter.printInformation(product.toString());
            }
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("The collection is empty.");
        }

    }

    /**
     * Adds an product to the collection.
     *
     * @param product The product to be added.
     */
    public static void addProduct(Product product) {
        priorityQueue.add(product);
    }

    /**
     * Adds an product to the collection if it has the maximum value based on the natural ordering.
     * If the collection is empty, the product is added automatically.
     *
     * @param product The product to be added.
     */
    public static void addIfMin(Product product) {
        if (priorityQueue.size() == 0) {
            addProduct(product);
        } else {
            addIfMinFlag = true;
            for (Product product1 : priorityQueue) {
                if (product1.compareTo(product) < 0) {
                    addIfMinFlag = false;
                    break;
                }
            }
            if (addIfMinFlag) addProduct(product);
        }
    }
    /**
     * Updates an element in the collection with the specified ID.
     * The attributes of the product are updated with the attributes of the newProduct.
     *
     * @param newProduct The updated product.
     * @param ID              The ID of the product to be updated.
     */
    public static void updateElement(Product newProduct, Integer ID) {
        priorityQueue.forEach(product -> {
            if (product.getId().equals(ID)) {
                product.setName(newProduct.getName());
                product.setCoordinates(newProduct.getCoordinates());
                product.setPrice(newProduct.getPrice());
                product.setUnitOfMeasure(newProduct.getUnitOfMeasure());
                product.setOrganization(newProduct.getOrganization());
            }
        });
    }

    /**
     * Clears the collection by removing all products.
     * The removed products are stored in the clearList.
     */
    public static void clearCollection() {
        for (Product product : priorityQueue) {
            clearList.add(product);
        };
        priorityQueue.clear();
    }

    /**
     * Removes an product from the collection based on its ID.
     *
     * @param ID The ID of the product to be removed.
     */
    public  static void removeElement(Integer ID) {
        for (Product product : priorityQueue) {
            if (product.getId().equals(ID)) {
                priorityQueue.remove(product);
                break;
            }
        }
    }

    public static void getHistory() {
        int i = 1;
        for (String command : historyCommandList) {
            System.out.println(i + " " + command);
            i ++;
        }
    }

    public static void removeHead() {
        try {
            if (priorityQueue.size() == 0) throw new EmptyCollectionException();
            System.out.println("First product: " + priorityQueue.poll());
        } catch (EmptyCollectionException exception) {
            System.out.println("Collection is empty!");
        }
        System.out.println("Fisrt element was successfully deleted!");
    }

    public static void printDescendingOrder() {
        try {
            if (priorityQueue.isEmpty()) throw new EmptyCollectionException();
            PriorityQueue<Product> reversedPriorityQueue = new PriorityQueue<>(descendingOrderComparator);
            reversedPriorityQueue.addAll(priorityQueue);
            while (!reversedPriorityQueue.isEmpty()) {
                System.out.println(reversedPriorityQueue.poll());
            }
        } catch (EmptyCollectionException exception) {
            System.out.println("The collection is empty.");
        }
    }

    public static void printDescendingPriceOrder() {
        try {
            if (priorityQueue.isEmpty()) throw new EmptyCollectionException();
            PriorityQueue<Float> reversedPrice = new PriorityQueue<>(descendingOrderComparatorFloat);
            for (Product product : priorityQueue) {
                reversedPrice.add(product.getPrice());
            }
            while (!reversedPrice.isEmpty()) {
                System.out.println(reversedPrice.poll());
            }
        } catch (EmptyCollectionException exception) {
            System.out.println("The collection is empty.");
        }
    }

    public static boolean priceExistence(Float price) {
        for (Product product : priorityQueue) {
            return product.getPrice().equals(price);
        }
        return false;
    }

    public static void removeElementByPrice(Float price) {
        for (Product product : priorityQueue) {
            if (product.getPrice().equals(price)) {
                priorityQueue.remove(product);
                break;
            }
        }
    }

    /**
     * Loads the collection from a file with the specified name.
     *
     * @param fileName The name of the file to load the collection from.
     */
    public static void getCollectionFromFile(String fileName) {
        priorityQueue = CSVProcess.loadCollection(fileName);
    }

    /**
     * Saves the collection to a file.
     */
    public static void saveCollectionToFile() {
        CSVProcess.writeCollection();
    }

    /**
     * Returns the addIfMinFlag value.
     *
     * @return The addIfMinFlag value.
     */
    public static boolean getAddIfMinFlag() {
        return addIfMinFlag;
    }

}

