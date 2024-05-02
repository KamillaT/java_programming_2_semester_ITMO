package processing;

import data.Product;
import utilities.CollectionManager;
import utilities.Invoker;

import java.io.IOException;

public class ServerCommandProcessor {
    public String help() throws IOException {
        StringBuilder sb = new StringBuilder();
        Invoker.getCommands().forEach((name, command) -> sb.append(command.getCommandInformation()).append("\n"));
        return sb.toString();
    }
    public String add(Object object) throws IOException {
        return CollectionManager.add((Product) object);
    }

    public String addIfMin(Object object) throws IOException {
        return CollectionManager.addIfMin((Product) object);
    }

    public String info() throws IOException {
        return CollectionManager.info();
    }

    public String clear() throws IOException {
        return CollectionManager.clear();
    }

    public String show() throws IOException {
        return CollectionManager.show();
    }

    public String history() throws IOException {
        return CollectionManager.history();
    }

    public String descendingOrder() throws IOException {
        return CollectionManager.descendingOrder();
    }

    public String descendingPriceOrder() throws IOException {
        return CollectionManager.descendingPriceOrder();
    }

    public String removeById(String sID) throws IOException {
        return CollectionManager.removeByID(sID);
    }

    public String removeByPrice(String sPrice) throws IOException {
        return CollectionManager.removeByPrice(sPrice);
    }

    public String removeHead() throws IOException {
        return CollectionManager.removeHead();
    }

    public String update(String sID, Object object) throws IOException {
        int ID = Integer.parseInt(sID);
        if (CollectionManager.idExistence(ID)) {
            CollectionManager.updateElement((Product) object, ID);
            return "The product has been updated";
        } else {
            return "This ID does not exist in this collection!";
        }
    }
}
