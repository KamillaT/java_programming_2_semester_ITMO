package processing.specificcommands;

import processing.ServerCommandProcessor;
import java.io.IOException;

public class AddIfMinCommand extends AbstractCommand{

    public AddIfMinCommand() {
        super("add_if_min {element}", "add a new element to a collection if its value is lower than the value of the smallest element of this collection");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor serverCommandProcessor = new ServerCommandProcessor();
        return serverCommandProcessor.addIfMin(object, userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
