package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.Scanner;

/**
 * The UpdateCommand class represents a command to update a collection element by its ID.
 * This command updates the value of a collection element whose ID is equal to a given one.
 */
public class UpdateCommand extends AbstractCommand {
    private final Receiver receiver;

    /**
     * Constructs an UpdateCommand with the specified receiver.
     *
     * @param receiver the receiver used to execute the command
     */
    public UpdateCommand(Receiver receiver) {
        super("update id {element}", "update the value of a collection element whose id is equal to a given one.");
        this.receiver = receiver;
    }

    /**
     * Executes the update command, updating the value of a collection element by its ID.
     *
     * @param arg the command arguments
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length != 2) throw new WrongAmountOfElementsException();
        receiver.update(arg[1], new Scanner(System.in));
    }

    /**
     * Displays information about the update command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
