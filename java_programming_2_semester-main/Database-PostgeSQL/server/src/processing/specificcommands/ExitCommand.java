package processing.specificcommands;

public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "end the program (without saving to a file)");
    }

    @Override
    public String execute(String string, Object object, Integer userId) {
        return "";
    }

    @Override
    public String getCommandInformation() {
        return super.toString();
    }
}
