package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The RemoveByIdCommand class represents a command to remove an element from a collection by its id.
 * This command is used to remove an organization from the collection based on its id.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a RemoveByIdCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public RemoveByIdCommand(Receiver receiver) {
        super("remove_by_id ", "remove an element from a collection by its id");
        this.receiver = receiver;
    }

    /**
     * Executes the remove_by_id command, removing an element from the collection by its id.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        receiver.removeById(arg[1]);
    }

    /**
     * Displays information about the remove_by_id command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
