package utility.mode;

import commands.Invoker;
import commands.Receiver;
import utility.ConsolePrinter;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The UserInputMode class represents the interactive mode for receiving user input and executing commands.
 */
public class UserInputMode implements IMode {
    private static Scanner userScanner;

    /**
     * Executes the interactive mode for receiving user input and executing commands.
     */
    @Override
    public void executeMode() {
        int commandStatus;
        try {
            userScanner = new Scanner(System.in);
            do{
                commandStatus = Invoker.executeCommand(userScanner.nextLine().trim().toLowerCase().split(" "));
            } while (userScanner.hasNextLine() && commandStatus !=2 );
        } catch (NoSuchElementException exception) {
             ConsolePrinter.printError("No user input detected!");
        } catch (IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected error!");
        }
    }

    /**
     * Retrieves the scanner used for reading user input in the interactive mode.
     *
     * @return The scanner used for reading user input.
     */
    @Override
    public  Scanner getScanner() {
        return userScanner;
    }

}
