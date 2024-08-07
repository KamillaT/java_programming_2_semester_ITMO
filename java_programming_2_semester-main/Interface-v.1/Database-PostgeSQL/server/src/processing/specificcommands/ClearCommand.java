package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class ClearCommand extends AbstractCommand {
    public ClearCommand() {
        super("clear", "clear collection");
    }

    @Override
    public String execute(String argument,Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.clear(userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
