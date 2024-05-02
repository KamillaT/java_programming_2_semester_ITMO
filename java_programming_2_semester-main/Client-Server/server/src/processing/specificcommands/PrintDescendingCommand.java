package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class PrintDescendingCommand extends AbstractCommand {
    public PrintDescendingCommand() {
        super("print_descending", "display the elements of the collection in descending order");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.descendingOrder();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}

