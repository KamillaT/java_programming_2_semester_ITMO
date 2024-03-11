package commands;

import exceptions.WrongAmountOfElementsException;

/**
 * The HistoryCommand class represents a command to display the command history.
 * It extends the Command class.
 */
public class HistoryCommand extends AbstractCommand {

    private Receiver receiver;

    /**
     * Constructs an HistoryCommand object with the specified receiver.
     *
     * @param receiver the receiver to execute the command
     */
    public HistoryCommand(Receiver receiver) {
        super("history", "print commands history");
        this.receiver = receiver;
    }

    /**
     * Executes the history command, printing last 11 commands.
     *
     * @param arg The command argument (not used for this command).
     * @throws WrongAmountOfElementsException if the number of arguments is incorrect
     */
    @Override
    public void execute(String[] arg) throws WrongAmountOfElementsException {
        if (arg.length == 0) throw new WrongAmountOfElementsException();
        receiver.history();
    }

    /**
     * Retrieves information about the info command.
     */
    @Override
    public void getCommandInformation() {
        System.out.println(super.toString());
    }
}