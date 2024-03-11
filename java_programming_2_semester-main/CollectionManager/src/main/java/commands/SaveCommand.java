package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The SaveCommand class represents a command to save the collection to a file.
 * This command triggers the saving of the collection data to a file.
 */
public class SaveCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs a SaveCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public SaveCommand(Receiver receiver) {
        super("save", "save the collection to the file");
        this.receiver = receiver;
    }

    /**
     * Executes the save command, triggering the saving of the collection to a file.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.save();
    }

    /**
     * Displays information about the save command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
