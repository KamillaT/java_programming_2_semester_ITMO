package processing.specificcommands;

import processing.ServerCommandProcessor;
import java.io.IOException;

public class AddCommand extends AbstractCommand {
    public AddCommand(){
        super("add {element}", "add a new element to the collection");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.add(object, userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
