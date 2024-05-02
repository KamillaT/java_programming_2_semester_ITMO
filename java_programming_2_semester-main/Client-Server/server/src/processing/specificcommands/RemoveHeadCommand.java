package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class RemoveHeadCommand extends AbstractCommand {
    public RemoveHeadCommand() {
        super("remove_head", "remove a head element");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.removeHead();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}


