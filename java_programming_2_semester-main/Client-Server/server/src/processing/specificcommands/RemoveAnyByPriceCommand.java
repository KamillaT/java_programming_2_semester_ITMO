package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveAnyByPriceCommand extends AbstractCommand {
    public RemoveAnyByPriceCommand() {
        super("remove_any_by_price ", "delete one item from the collection, the value of the price field of which is equivalent to the specified one");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.removeByPrice(argument);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}

