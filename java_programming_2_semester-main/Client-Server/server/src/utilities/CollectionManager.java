package utilities;

import data.*;
import file.CSVProcess;
import utility.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.Collectors;

public class CollectionManager {
    private static PriorityQueue<Product> priorityQueue;
    private static LocalDateTime initializationDate;
    public static Stack<String> historyCommandList = new Stack<>();

    public static void initializationCollection() {
        priorityQueue = new PriorityQueue<>();
        initializationDate = LocalDateTime.now();
    }

    public static boolean idExistence(int ID) {
        return priorityQueue.stream().anyMatch(product -> product.getId() == ID);
    }

    public static PriorityQueue<Product> getCollection() {
        return priorityQueue;
    }

    public static void pushCommand(String name) {
        if (historyCommandList.size() == 11) {
            historyCommandList.remove(0);
        }
        historyCommandList.push(name);
    }

    public static String add(Product product) {
        pushCommand("add");
        product.setId(IDGenerator.generateID());
        priorityQueue.add(product);
        return "Product has been added to the collection!";
    }

    public static String clear() {
        pushCommand("clear");
        priorityQueue.clear();
        return "The collection is empty!";
    }

    public static String addIfMin(Product product) {
        pushCommand("add_if_min");
        if (priorityQueue.isEmpty() || priorityQueue.stream().allMatch(pr -> pr.compareTo(product) >= 0)) {
            add(product);
            return "The product has been added to the collection!";
        }
        return "The product can not be added to the collection!";
    }

    public static String info() {
        pushCommand("info");
        return "Type of collection: " + priorityQueue.getClass().getName() + ".\nInitialization Date: " + initializationDate
                + ".\nNumber of elements: " + priorityQueue.size() + ".";
    }

    public static String history() {
        pushCommand("history");
        String result = "";
        Integer i = 1;
        for (String s : historyCommandList) {
            result += i + ") " + s + "\n";
            i++;
        }
        return result;
    }

    public static String descendingOrder() {
        pushCommand("print_descending");
        if (priorityQueue.isEmpty()) return "The collection is empty!";
        return priorityQueue.stream()
                .sorted(Comparator.reverseOrder())
                .map(Product::toString)
                .collect(Collectors.joining());
    }

    public static String descendingPriceOrder() {
        pushCommand("print_field_descending_price");
        if (priorityQueue.isEmpty()) return "The collection is empty!";
        PriorityQueue<Float> reversedPrice = new PriorityQueue<>(Comparator.reverseOrder());
        for (Product product : priorityQueue) {
            reversedPrice.add(product.getPrice());
        }
        return reversedPrice.stream()
                .sorted(Comparator.reverseOrder())
                .map(Object::toString)
                .collect(Collectors.joining("\n"));

    }

    public static String removeByID(String sID) {
        pushCommand("remove_by_id");
        if (priorityQueue.isEmpty()) return "The collection is empty!";
        Integer ID = Integer.parseInt(sID);
        priorityQueue.removeIf(product -> product.getId().equals(ID));
        return "The product with this ID has been removed!";
    }

    public static String removeByPrice(String sPrice) {
        pushCommand("remove_any_by_price");
        if (priorityQueue.isEmpty()) return "The collection is empty!";
        Float Price = Float.parseFloat(sPrice);
        priorityQueue.removeIf(product -> product.getPrice().equals(Price));
        return "The product with this price has been removed!";
    }

    public static String removeHead() {
        pushCommand("remove_head");
        if (priorityQueue.isEmpty()) return "The collection is empty!";
        priorityQueue.poll();
        return "The first element of the collection has been removed!";
    }

    public static String show() {
        pushCommand("show");
        if (priorityQueue.isEmpty()) return "The collection is empty!";
        return priorityQueue.stream()
                .sorted()
                .map(Product::toString)
                .collect(Collectors.joining());
    }
    public static void updateElement(Product newProduct, Integer ID) {
        pushCommand("update");
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
    public static void getCollectionFromFile(String fileName) {
        priorityQueue = CSVProcess.loadCollection(fileName);
    }
}
