package utility;

import commands.*;
import utility.csv.CSVProcess;
import utility.csv.CSVReader;
import utility.mode.UserInputMode;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The {@code Console} class represents a utility for managing commands and interacting with the program via the command line.
 * It initializes the invoker with registered commands and handles user input.
 */
public class Console {
    /**
     * Initializes the invoker with registered commands.
     * Registers all available commands with the invoker.
     */
    public static void starter(String pathToFile) {
        CSVProcess.setPathToFile(pathToFile);
        CollectionManager.getCollectionFromFile(CSVProcess.getPathToFile());
        CommandManager.commandStarter();
        UserInputMode userInputMode = new UserInputMode();
        userInputMode.executeMode();
    }
}
