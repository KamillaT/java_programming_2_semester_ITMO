package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class RemoveByIdCommand extends AbstractCommand {
    public RemoveByIdCommand() {
        super("remove_by_id ", "remove an element from a collection by its id");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.removeById(argument);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
