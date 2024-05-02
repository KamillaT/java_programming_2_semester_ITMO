package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class InfoCommand extends AbstractCommand {
    public InfoCommand() {
        super("info", "print information about the collection");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.info();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
