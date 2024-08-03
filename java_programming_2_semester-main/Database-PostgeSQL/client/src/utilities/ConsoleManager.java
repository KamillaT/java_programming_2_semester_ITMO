package utilities;

import connector.Communicator;
import data.UserInfo;
import mode.UserInputMode;
import processing.CommandHandler;
import utility.ConsolePrinter;

import java.util.Scanner;

public class ConsoleManager {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static Communicator communicator;

    private static String readLogin(Scanner scanner) {
        ConsolePrinter.printInformation("Enter your login please: ");
        String login = scanner.nextLine();

        if (!UserInfo.isLoginValid(login)) {
            ConsolePrinter.printError("Login's length must be between 4 and 128 symbols" +
                    "and login can contain only digits and letters.\n" +
                    "Try again.");
            return readLogin(scanner);
        }

        return login;
    }

    private static String readPassword(Scanner scanner) {
        ConsolePrinter.printInformation("Enter your password please: ");
        String password = scanner.nextLine();

        if (!UserInfo.isPasswordValid(password)) {
            ConsolePrinter.printError("Password's length must be between 4 and 128 symbols" +
                    "and password can contain only digits and letters.\n" +
                    "Try again.");
            return readPassword(scanner);
        }

        return password;
    }

    private static CommandHandler constrictCommunicatorAndEstablishConnection(String host, int port, Scanner scanner) {
        String login = readLogin(scanner);
        String password = readPassword(scanner);

        Communicator communicator = new Communicator(host,
                port,
                RECONNECTION_TIMEOUT,
                MAX_RECONNECTION_ATTEMPTS,
                login,
                password);

        CommandHandler commandHandler = new CommandHandler(communicator);

        if (commandHandler.executeCommand(new String[]{"info", ""}) == -1) {
            ConsolePrinter.printError("Unable to login to the server! Try again.");
            return constrictCommunicatorAndEstablishConnection(host, port, scanner);
        }

        return commandHandler;
    }

    public static void interactive(String host, String sPort) {
        try {
            Scanner scanner = new Scanner(System.in);
            int port = Integer.parseInt(sPort);

            ConsolePrinter.printInformation("Welcome to my application!");

            CommandHandler commandHandler = constrictCommunicatorAndEstablishConnection(host, port, scanner);
            UserInputMode userInputMode = new UserInputMode();

            ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
            userInputMode.executeMode(commandHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
