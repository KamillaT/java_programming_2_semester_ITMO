package commands;

import exceptions.WrongAmountOfElementsException;
public class PrintFieldDescendingPriceCommand extends AbstractCommand{
    private final Receiver receiver;

    /**
     * Constructs a RemoveByIdCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public PrintFieldDescendingPriceCommand(Receiver receiver) {
        super("print_field_descending_price", "print the values of the price field of all elements in descending order");
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
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.descendingPriceOrder();
    }

    /**
     * Displays information about the remove_by_id command.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}
