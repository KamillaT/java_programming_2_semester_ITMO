package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class UpdateCommand extends AbstractCommand {
    public UpdateCommand() {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.update(argument, object, userId);
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
