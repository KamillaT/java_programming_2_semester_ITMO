package processing.specificcommands;

import processing.ServerCommandProcessor;

public class RemoveByIdCommand extends AbstractCommand {
    public RemoveByIdCommand() {
        super("remove_by_id ", "remove an element from a collection by its id");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.removeById(argument, userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
