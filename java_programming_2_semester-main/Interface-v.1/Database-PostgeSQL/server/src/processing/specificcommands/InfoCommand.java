package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class InfoCommand extends AbstractCommand {
    public InfoCommand() {
        super("info", "print information about the collection");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.info(userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
