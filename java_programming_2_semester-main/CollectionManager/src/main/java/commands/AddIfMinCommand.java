package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The AddIfMaxCommand class represents a command to add a new element to a collection
 * if its value is greater than the value of the largest element of this collection.
 * It extends the AbstractCommand class.
 */
public class AddIfMinCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs an AddIfMaxCommand object with the specified receiver.
     *
     * @param receiver the receiver to add the new element if it is maximum
     */
    public AddIfMinCommand(Receiver receiver) {
        super("add_if_min {element}", "add a new element to a collection if its value is lower than the value of the smallest element of this collection");
        this.receiver = receiver;
    }

    /**
     * Executes the add_if_max command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.addIfMin(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add_if_max command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
