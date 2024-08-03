package processing.specificcommands;

public class ExecuteScriptCommand extends AbstractCommand {

    public ExecuteScriptCommand() {
        super("execute_script file_name", "read and execute the script from the specified file");
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
