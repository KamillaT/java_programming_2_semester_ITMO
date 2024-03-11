package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

/**
 * The HelpCommand class represents a command that provides help information.
 * This command is typically used to display information about how to use the application
 * or to provide details about other available commands.
 */
public class HelpCommand extends AbstractCommand {

    private Receiver receiver;

    /**
     * Constructs a HelpCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public HelpCommand(Receiver receiver) {
        super("help", "display help on available commands");
        this.receiver = receiver;
    }

    /**
     * Executes the help command, displaying information about how to use the application
     * or providing details about other available commands.
     *
     * @param arg The command argument (not used for this command).
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.help();
    }

    /**
     * Retrieves information about the help command.
     */
    @Override
    public void getCommandInformation() {
        ConsolePrinter.printInformation(super.toString());
    }
}
