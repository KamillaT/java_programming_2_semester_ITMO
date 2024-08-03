package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class PrintDescendingCommand extends AbstractCommand {
    public PrintDescendingCommand() {
        super("print_descending", "display the elements of the collection in descending order");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.descendingOrder(userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}

