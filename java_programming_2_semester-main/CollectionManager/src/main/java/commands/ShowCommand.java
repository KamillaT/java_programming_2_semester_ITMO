package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The ShowCommand class represents a command to print all elements of the collection.
 * This command prints all elements of the collection in string representation to the standard output.
 */
public class ShowCommand extends AbstractCommand {
    private Receiver receiver;

    /**
     * Constructs a ShowCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public ShowCommand(Receiver receiver) {
        super("show", "print to standard output all the elements of the collection in string representation.");
        this.receiver = receiver;
    }

    /**
     * Executes the show command, printing all elements of the collection to the standard output.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.show();
    }

    /**
     * Displays information about the show command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
