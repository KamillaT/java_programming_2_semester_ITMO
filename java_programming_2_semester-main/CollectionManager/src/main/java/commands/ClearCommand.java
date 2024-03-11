package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The ClearCommand class represents a command to clear the collection.
 * It extends the AbstractCommand class.
 */
public class ClearCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a ClearCommand object with the specified receiver.
     *
     * @param receiver the receiver to clear the collection
     */
    public ClearCommand(Receiver receiver) {
        super("clear", "clear collection");
        this.receiver = receiver;
    }

    /**
     * Executes the clear command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.clear();
    }

    /**
     * Retrieves information about the clear command.
     */
    @Override
    public void getCommandInformation() { ConsolePrinter.printInformation(super.toString());
    }
}
