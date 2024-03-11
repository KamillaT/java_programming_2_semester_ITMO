package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The ExitCommand class represents a command to end the program without saving to a file.
 * It extends the AbstractCommand class.
 */
public class ExitCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs an ExitCommand object with the specified receiver.
     *
     * @param receiver the receiver to end the program
     */
    public ExitCommand(Receiver receiver) {
        super("exit", "end the program (without saving to a file).");
        this.receiver = receiver;
    }

    /**
     * Executes the exit command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.exit();
    }

    /**
     * Retrieves information about the exit command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
