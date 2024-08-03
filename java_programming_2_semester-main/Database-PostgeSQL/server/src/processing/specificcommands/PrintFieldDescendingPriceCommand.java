package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class PrintFieldDescendingPriceCommand extends AbstractCommand {
    public PrintFieldDescendingPriceCommand() {
        super("print_field_descending_price", "print the values of the price field of all elements in descending order");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.descendingPriceOrder(userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}

