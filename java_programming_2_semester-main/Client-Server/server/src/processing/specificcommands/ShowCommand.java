package processing.specificcommands;

import processing.ServerCommandProcessor;
import java.io.IOException;

public class ShowCommand extends AbstractCommand {
    public ShowCommand() {
        super("show", "print to standard output all the elements of the collection in string representation.");
    }

    @Override
    public String execute(String argument, Object object) {
        try {
            ServerCommandProcessor commandProcessor = new ServerCommandProcessor();
            return commandProcessor.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
