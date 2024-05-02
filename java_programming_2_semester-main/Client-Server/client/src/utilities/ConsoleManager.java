package utilities;

import connector.Communicator;
import mode.UserInputMode;
import processing.CommandHandler;
import utility.ConsolePrinter;

public class ConsoleManager {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;

    public static void interactive(String host, String sPort) {
        try {
            int port = Integer.parseInt(sPort);
            Communicator communicator = new Communicator(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS);
            CommandHandler commandHandler = new CommandHandler(communicator);
            UserInputMode userInputMode = new UserInputMode();
            ConsolePrinter.printInformation("Welcome to my application!");
            ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
            userInputMode.executeMode(commandHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
