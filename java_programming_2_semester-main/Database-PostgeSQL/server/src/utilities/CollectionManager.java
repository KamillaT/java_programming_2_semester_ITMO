package utilities;

import data.Product;
import database.ActionResult;
import database.CollectionCommunicator;
import database.DatabaseManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CollectionManager {
    private final PriorityQueue<Product> priorityQueue = new PriorityQueue<>();
    private LocalDateTime initializationDate;
    private final CollectionCommunicator collectionCommunicator;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public ConcurrentMap<Integer, Stack<String>> commandsHistory = new ConcurrentHashMap<>();

    public CollectionManager(DatabaseManager databaseManager, String psqlUrl, String psqlLogin, String psqlPassword) throws SQLException {
        databaseManager.connect(psqlUrl, psqlLogin, psqlPassword);
        this.collectionCommunicator = new CollectionCommunicator(databaseManager);

        initializationCollection();
    }

    public void initializationCollection() {
        initializationDate = LocalDateTime.now();
        priorityQueue.addAll(collectionCommunicator.loadProducts());
    }

    public boolean isIdPresent(int ID, int userId) {
        try (var withLock = AutoReadUnlock.lock(readWriteLock)) {
            return priorityQueue.stream().anyMatch(product -> product.getId() == ID
            && product.getCreatorId() == userId);
        }
    }

    public void pushCommand(String name, Integer userId) {
        if (!commandsHistory.containsKey(userId)) {
            commandsHistory.put(userId, new Stack<>());
        }

        if (commandsHistory.get(userId).size() == 11) {
            commandsHistory.get(userId).remove(0);
        }

        commandsHistory.get(userId).push(name);
    }

    public String add(Product product, Integer userId) {
        pushCommand("add", userId);
        product.setCreatorId(userId);

        AbstractMap.SimpleEntry<Integer, Long> productAndOrganizationId = collectionCommunicator.addProduct(product);

        if (productAndOrganizationId.getKey() < 0) {
            return "Unable to add product to the collection!";
        }

        product.setId(productAndOrganizationId.getKey());
        product.getOrganization().setId(productAndOrganizationId.getValue());

        try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
            priorityQueue.add(product);
        }

        return "Product has been added to the collection!";
    }

    public String clear(Integer userId) {
        pushCommand("clear", userId);

        if (collectionCommunicator.removeAllUsersProducts(userId) == ActionResult.SUCCESS) {
            try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
                priorityQueue.removeIf(
                        elem -> elem.getCreatorId().equals(userId)
                );
            }

            return "All your products have been removed from the collection";
        }

        return "An error occurred while clearing the collection!";
    }

    public String addIfMin(Product product, Integer userId) {
        pushCommand("add_if_min", userId);

        try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty() ||
                    priorityQueue
                            .stream()
                            .filter(elem -> elem.getCreatorId().equals(userId))
                            .allMatch(pr -> pr.compareTo(product) >= 0)) {


                return add(product, userId);
            }
        }

        return "The product can not be added to the collection!";
    }

    public String info(Integer userId) {
        pushCommand("info", userId);

        try (var withLock = AutoReadUnlock.lock(readWriteLock)) {
            return "Type of collection: " + priorityQueue.getClass().getName() +
                    ".\nInitialization Date: " + initializationDate
                    + ".\nNumber of elements: " + priorityQueue.size() + ".";
        }
    }

    public String history(Integer userId) {
        pushCommand("history", userId);
        String result = "";
        Integer i = 1;

        for (String s : commandsHistory.get(userId)) {
            result += i + ") " + s + "\n";
            i++;
        }

        return result;
    }

    public String descendingOrder(Integer userId) {
        pushCommand("print_descending", userId);

        try (var withLock = AutoReadUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty()) {
                return "The collection is empty!";
            }

            return priorityQueue.stream()
                    .sorted(Comparator.reverseOrder())
                    .map(Product::toString)
                    .collect(Collectors.joining());
        }
    }

    public String descendingPriceOrder(Integer userId) {
        pushCommand("print_field_descending_price", userId);
        try (var withLock = AutoReadUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty()) {
                return "The collection is empty!";
            }

            return priorityQueue
                    .stream()
                    .sorted(Comparator.reverseOrder())
                    .map(product -> product.getPrice().toString())
                    .collect(Collectors.joining("\n"));
        }

    }

    public String removeByID(String sID, Integer userId) {
        pushCommand("remove_by_id", userId);
        Integer ID = Integer.parseInt(sID);

        try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty()) {
                return "The collection is empty!";
            }


            boolean wasRemoved = collectionCommunicator.removeProductById(ID, userId) == ActionResult.SUCCESS &&
                    priorityQueue.removeIf(
                            product -> product.getId().equals(ID) &&
                                    product.getCreatorId().equals(userId)
                    );

            return wasRemoved ? "The product with this ID has been removed!" : "Unable to find product with given id!";
        }
    }

    public String removeByPrice(String sPrice, Integer userId) {
        pushCommand("remove_any_by_price", userId);
        Float price = Float.parseFloat(sPrice);

        try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty()) {
                return "The collection is empty!";
            }

            boolean wasProductRemoved = collectionCommunicator.removeProductByPrice(price, userId) == ActionResult.SUCCESS &&
                    priorityQueue.removeIf(
                            product -> product.getPrice().equals(price) &&
                                    product.getCreatorId().equals(userId)
                    );

            return wasProductRemoved ?
                    "The product with this price has been removed!"
                    : "Unable to remove product from the collection";
        }
    }

    public String removeHead(Integer userId) {
        pushCommand("remove_head", userId);

        try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty()) {
                return "The collection is empty!";
            }

            Optional<Product> firstUsersProduct = priorityQueue
                    .stream()
                    .filter(elem -> elem.getCreatorId().equals(userId))
                    .findFirst();

            firstUsersProduct.ifPresent(product -> {
                collectionCommunicator.removeProductById(product.getId(), userId);
                priorityQueue.remove(product);
            });

            return firstUsersProduct.isEmpty() ?
                    "You do not have any products in the collection!"
                    : "The first element of the collection has been removed!";
        }
    }

    public String show(Integer userId) {
        pushCommand("show", userId);

        try (var withLock = AutoReadUnlock.lock(readWriteLock)) {
            if (priorityQueue.isEmpty()) {
                return "The collection is empty!";
            }

            return priorityQueue
                    .stream()
                    .sorted()
                    .map(Product::toString)
                    .collect(Collectors.joining());
        }
    }

    public void updateElement(Product newProduct, Integer ID, Integer userId) {
        pushCommand("update", userId);

        try (var withLock = AutoWriteUnlock.lock(readWriteLock)) {
            priorityQueue
                    .stream()
                    .filter(product ->
                            product.getId().equals(ID) &&
                                    product.getCreatorId().equals(userId))
                    .findFirst()
                    .ifPresent(product ->
                            {
                                collectionCommunicator.updateProduct(product);

                                product.setName(newProduct.getName());
                                product.setCoordinates(newProduct.getCoordinates());
                                product.setPrice(newProduct.getPrice());
                                product.setUnitOfMeasure(newProduct.getUnitOfMeasure());
                                product.setOrganization(newProduct.getOrganization());
                            }
                    );
        }
    }
}
