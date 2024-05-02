package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help", "display help on available commands");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.help();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}