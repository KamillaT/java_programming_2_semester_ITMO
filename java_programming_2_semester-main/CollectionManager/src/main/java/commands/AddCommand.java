package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The AddCommand class represents a command to add a new element to the collection.
 * It extends the AbstractCommand class.
 */
public class AddCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs an AddCommand object with the specified receiver.
     *
     * @param receiver the receiver to add the new element
     */
    public AddCommand(Receiver receiver) {
        super("add {element}", "add a new element to the collection");
        this.receiver = receiver;
    }

    /**
     * Executes the add command.
     *
     * @param arg the arguments for the command
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.add(new Scanner(System.in));
    }

    /**
     * Retrieves information about the add command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
