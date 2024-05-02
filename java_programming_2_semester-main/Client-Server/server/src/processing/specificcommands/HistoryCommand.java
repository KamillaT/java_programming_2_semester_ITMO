package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;


public class HistoryCommand extends AbstractCommand {
    public HistoryCommand() {
        super("history", "print commands history");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.history();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
