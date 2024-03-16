package commands;

import data.Product;
import exceptions.EmptyCollectionException;
import utility.CollectionManager;
import utility.ConsolePrinter;
import utility.creator.ProductCreator;
import utility.mode.FileScriptMode;

import java.util.Scanner;

/**
 * Utility class for managing commands in the program.
 */
public class Receiver {
    private final Invoker invoker;

    public Receiver (Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Execute the help command
     */
    public void help() {
        pushCommand("help");
        invoker.getCommands().forEach((name, command) -> command.getCommandInformation());
        ConsolePrinter.printResult("The 'help' command has been executed successfully!");
    }

    /**
     * Execute the info command
     */
    public void info() {
        pushCommand("info");
        CollectionManager.information();
        ConsolePrinter.printResult("The 'info' command has been executed successfully!");
    }

    /**
     * Execute the show command
     */
    public void show() {
        pushCommand("show");
        CollectionManager.fullInformation();
        ConsolePrinter.printResult("The 'show' command has been executed successfully!");
    }

    /**
     * Execute the add command
     */
    public void add(Scanner scanner) {
        pushCommand("add");
        Product product = ProductCreator.productCreator(scanner);
        CollectionManager.addProduct(product);
        ConsolePrinter.printResult("The 'add' command has been executed successfully!");
        CollectionManager.productStack.push(product);
    }

    /**
     * Execute the add_if_max command
     */
    public void addIfMin(Scanner scanner) {
        pushCommand("add_if_min");
        Product product = ProductCreator.productCreator(scanner);
        CollectionManager.addIfMin(product);
        if (CollectionManager.getAddIfMinFlag()) {
            CollectionManager.productStack.push(product);
        }
        ConsolePrinter.printResult("The 'add_if_min' command has been executed successfully!");
    }

    /**
     * Execute the update command
     */
    public void update(String sID, Scanner scanner) {
        int ID;
        pushCommand("update");
        try {
            if (CollectionManager.getCollection().size() == 0) throw new EmptyCollectionException();
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                Product tempProduct1 = CollectionManager.getProductByID(ID);
                Product oldProduct = tempProduct1.clone();
                CollectionManager.productStack.push(oldProduct);
                Product newProduct = ProductCreator.productCreator(scanner);
                CollectionManager.updateElement(newProduct, ID);
                Product tempProduct = CollectionManager.getProductByID(ID);
                CollectionManager.productStack.push(tempProduct);
                ConsolePrinter.printResult("The 'update' command has been executed successfully");
                ConsolePrinter.printResult("Product id " + ID+ " is updated!");
            } else {
                ConsolePrinter.printError("The product with this ID does not exist in the collection!");
            }
        } catch (NumberFormatException exception) {
            ConsolePrinter.printError("The ID is not correct!");
        } catch (EmptyCollectionException exception) {
            ConsolePrinter.printError("The collection is empty");
        }
    }

    /**
     * Execute the remove_by_id command
     */
    public void removeById(String sID) {
        Integer ID;
        pushCommand("remove");
        try {
            ID = Integer.parseInt(sID);
            if (CollectionManager.idExistence(ID)) {
                Product product = CollectionManager.getProductByID(ID);
                CollectionManager.productStack.push(product);
                CollectionManager.removeElement(ID);
                ConsolePrinter.printResult("The 'remove_by_id' command has been executed successfully!");
                ConsolePrinter.printResult("The product with this ID has been deleted!");
            } else {
                ConsolePrinter.printError("The product with this ID does not exist!");
            }
        } catch (NumberFormatException exception) {
            ConsolePrinter.printError("Invalid command argument!");
        }
    }

    /**
     * Execute the clear command
     */
    public void clear() {
        pushCommand("clear");
        CollectionManager.clearCollection();
        ConsolePrinter.printResult("The 'clear' command has been executed successfully!");
        ConsolePrinter.printResult("The collection is cleared!");
    }

    /**
     * Execute the execute_script command
     */
    public void executeScript(String path) {
        pushCommand("execute_script");
        FileScriptMode fileScriptMode = new FileScriptMode(path);
        fileScriptMode.executeMode();
        ConsolePrinter.printResult("The 'execute_script' command has been executed successfully!");
    }

    /**
     * Execute the exit command
     */
    public void exit() {
        ConsolePrinter.printResult("The 'exit' command has been executed successfully!");
        ConsolePrinter.printResult("Program has been closed!");
        System.exit(0);
    }

    /**
     * Execute the save command
     */
    public void save() {
        pushCommand("save");
        ConsolePrinter.printResult("The 'save' command has been executed successfully!");
        ConsolePrinter.printResult("The collection has been saved in the file!");
        CollectionManager.saveCollectionToFile();
    }

    /**
     * Execute the history command
     */
    public void history() {
        pushCommand("history");
        CollectionManager.getHistory();
        System.out.println("The 'history' command has been executed successfully");
    }

    /**
     * Push commands to history
     */
    public void pushCommand(String name) {
        if (CollectionManager.historyCommandList.size() == 11) {
            CollectionManager.historyCommandList.pop();
        }
        CollectionManager.historyCommandList.push(name);
    }

    /**
     * Execute the remove_by_price command
     */
    public void removeByPrice(String sPrice) {
        Float Price;
        pushCommand("remove_by_price");
        try {
            Price = Float.parseFloat(sPrice);
            if (CollectionManager.priceExistence(Price)) {
                CollectionManager.removeElementByPrice(Price);
                System.out.println("The 'remove_by_price' command has been executed successfully");
                System.out.println("The product with this price has been deleted!");
            } else {
                System.out.println("The product with this price does not exist!");
            }
        } catch (NumberFormatException exception) {
            System.out.println("Invalid command argument!");
        }
    }

    /**
     * Execute the remove_head command
     */
    public void removeHead() {
        pushCommand("remove_head");
        System.out.println("The 'remove_head' command has been executed successfully");
        CollectionManager.removeHead();
    }

    /**
     * Execute the print_descending command
     */
    public void descendingOrder() {
        pushCommand("print_descending");
        System.out.println("The 'print_descending' command has been executed successfully");
        CollectionManager.printDescendingOrder();
    }

    /**
     * Execute the print_field_descending_price command
     */
    public void descendingPriceOrder() {
        pushCommand("print_field_descending_price");
        System.out.println("The 'print_field_descending_price' command has been executed successfully");
        CollectionManager.printDescendingPriceOrder();
    }
}
