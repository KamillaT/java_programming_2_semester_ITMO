package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The ExecuteScriptCommand class represents a command to read and execute the script from the specified file.
 * It extends the AbstractCommand class.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    private final Receiver receiver;
    private static String path;

    /**
     * Constructs an ExecuteScriptCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the script
     */
    public ExecuteScriptCommand(Receiver receiver) {
        super("execute_script file_name", "read and execute the script from the specified file.");
        this.receiver = receiver;
    }

    /**
     * Executes the execute_script command.
     *
     * @param arg the arguments for the command
     * @throws StackOverflowError            if a stack overflow occurs
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws StackOverflowError, WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        try {
            if (System.getenv("HOMEPATH") != null) {
                path = System.getenv("HOMEPATH");
                path += "\\" + arg[1];
            } else if (System.getenv("HOME") != null) {
                path = System.getenv("HOME");
                path += "/" + arg[1];
            }
            receiver.executeScript(path);
        } catch (StackOverflowError error) {
            ConsolePrinter.printError("Stack overflow occurred");
        }
    }

    /**
     * Retrieves information about the execute_script command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
