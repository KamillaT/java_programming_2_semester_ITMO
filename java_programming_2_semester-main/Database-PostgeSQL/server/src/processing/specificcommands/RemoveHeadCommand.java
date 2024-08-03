package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class RemoveHeadCommand extends AbstractCommand {
    public RemoveHeadCommand() {
        super("remove_head", "remove a head element");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.removeHead(userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}


