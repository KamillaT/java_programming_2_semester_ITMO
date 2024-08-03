package processing;

import data.Product;
import database.DatabaseManager;
import utilities.CollectionManager;
import utilities.Invoker;

import java.sql.SQLException;

public class ServerCommandProcessor {
    private static final DatabaseManager databaseManager = new DatabaseManager();
    private static final CollectionManager collectionManager;

    static {
        try {
            collectionManager = new CollectionManager(
                    databaseManager,
                    System.getenv("PSQL_URL"),
                    System.getenv("PSQL_LOGIN"),
                    System.getenv("PSQL_PASSWORD")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public String help() {
        StringBuilder sb = new StringBuilder();
        Invoker.getCommands().forEach((name, command) -> sb.append(command.getCommandInformation()).append("\n"));
        return sb.toString();
    }

    public String add(Object object, Integer userId) {
        return collectionManager.add((Product) object, userId);
    }

    public String addIfMin(Object object, Integer userId) {
        return collectionManager.addIfMin((Product) object, userId);
    }

    public String info(Integer userId) {
        return collectionManager.info(userId);
    }

    public String clear(Integer userId) {
        return collectionManager.clear(userId);
    }

    public String show(Integer userId) {
        return collectionManager.show(userId);
    }

    public String history(Integer userId) {
        return collectionManager.history(userId);
    }

    public String descendingOrder(Integer userId) {
        return collectionManager.descendingOrder(userId);
    }

    public String descendingPriceOrder(Integer userId) {
        return collectionManager.descendingPriceOrder(userId);
    }

    public String removeById(String sID, Integer userId) {
        return collectionManager.removeByID(sID, userId);
    }

    public String removeByPrice(String sPrice, Integer userId) {
        return collectionManager.removeByPrice(sPrice, userId);
    }

    public String removeHead(Integer userId) {
        return collectionManager.removeHead(userId);
    }

    public String update(String sID, Object object, Integer userId) {
        int ID = Integer.parseInt(sID);

        if (collectionManager.isIdPresent(ID, userId)) {
            collectionManager.updateElement((Product) object, ID, userId);
            return "The product has been updated";
        } else {
            return "This ID does not exist in this collection!";
        }
    }
}
