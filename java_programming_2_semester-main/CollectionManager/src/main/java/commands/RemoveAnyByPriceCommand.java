package commands;

import exceptions.WrongAmountOfElementsException;

/**
 * The RemoveAnyByPriceCommand class represents a command to remove an element from a collection by its id.
 * This command is used to remove an organization from the collection based on its id.
 */
public class RemoveAnyByPriceCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a RemoveAnyByPriceCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public RemoveAnyByPriceCommand(Receiver receiver) {
        super("remove_any_by_price price", "delete one item from the collection, the value of the price field of which is equivalent to the specified one");
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
        receiver.removeByPrice(arg[1]);
    }

    /**
     * Displays information about the remove_by_id command.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
