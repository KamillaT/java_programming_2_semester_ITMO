package mode;

import processing.CommandHandler;
import utility.ConsolePrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInputMode implements IMode {
    private String[] commands = {"",""};
    /**
     * Executes the interactive mode for receiving user input and executing commands.
     */
    @Override
    public void executeMode(CommandHandler commandHandler) {
        int commandStatus;
        try {
            Scanner userScanner = new Scanner(System.in);
            do {
                String[] userCommand = userScanner.nextLine().trim().toLowerCase().split(" ");
                commands = userCommand.length > 1 ? userCommand : new String[]{userCommand[0], ""};
                commandStatus = commandHandler.executeCommand(commands);
            } while (userScanner.hasNextLine() && commandStatus != 2);
        } catch (NoSuchElementException exception) {
            ConsolePrinter.printError("No user input detected!");
        } catch (IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected error!");
        }
    }


}