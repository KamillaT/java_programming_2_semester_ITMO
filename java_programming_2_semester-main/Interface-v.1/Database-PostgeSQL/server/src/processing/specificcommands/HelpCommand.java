package processing.specificcommands;

import processing.ServerCommandProcessor;

import java.io.IOException;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help", "display help on available commands");
    }

    @Override
    public String execute(String argument, Object object, Integer userId) {
        ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
        return commandProcessor.help();
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}