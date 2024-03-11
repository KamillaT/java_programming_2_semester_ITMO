package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The InfoCommand class represents a command to print information about the collection.
 * It extends the AbstractCommand class.
 */
public class InfoCommand extends AbstractCommand {
    private Receiver receiver;

    /**
     * Constructs an InfoCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public InfoCommand(Receiver receiver) {
        super("info", "print information about the collection");
        this.receiver = receiver;
    }

    /**
     * Executes the info command, printing information about the collection.
     *
     * @param arg The command argument (not used for this command).
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.info();
    }

    /**
     * Retrieves information about the info command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
