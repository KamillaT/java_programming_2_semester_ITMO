package processing;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.WrongAmountOfElementsException;
import interaction.Request;
import interaction.Response;
import mode.FileScriptMode;
import utilities.CommandType;
import utility.ConsolePrinter;
import utility.ProductCreator;

import java.io.IOException;
import java.util.Scanner;

public class CommandHandler {
    private final Communicator communicator;
    private Scanner scanner = new Scanner(System.in);

    public CommandHandler(Communicator communicator) {
        this.communicator = communicator;
    }

    public int executeCommand(String[] commandSet) {
        CommandType commandType = checkCommand(commandSet[0], commandSet[1]);
        try {
            if (commandType.equals(CommandType.SIMPLE)) {
                Request commandRequest = new Request(commandSet[0]);
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.OBJECT)) {
                Request commandRequest = new Request(commandSet[0], ProductCreator.productCreator(scanner));
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.ARGUMENT)) {
                Request commandRequest = new Request(commandSet[0], commandSet[1]);
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.UPDATE)) {
                Request commandRequest = new Request(commandSet[0], ProductCreator.productCreator(scanner), commandSet[1]);
                processCommand(commandRequest);
                return 1;
            } else if (commandType.equals(CommandType.SCRIPT)) {
                FileScriptMode fileScriptMode = new FileScriptMode(commandSet[1]);
                fileScriptMode.executeMode(this);
                return 1;
            } else{
                ConsolePrinter.printError("The command " + commandSet[0] + " does not exist! Use command 'help' to get the available command list !");
                return 0;
            }
        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
            ConsolePrinter.printError("Exception occured while executing command ");
        }
        return 0;
    }


    private void processCommand(Request request) throws IOException, ClassNotFoundException {
        communicator.connect();
        Sender sender = new Sender(communicator.getSocketChannel().socket());
        sender.sendObject(request);
        Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
        Response response = receiver.receive();
        String answer = response.getAnswer();
        ConsolePrinter.printResult(answer);
        communicator.closeConnection();
    }

    private CommandType checkCommand(String command, String commandArgument) {
        try {
            switch (command.toLowerCase()) {
                case "help", "clear", "remove_head", "info", "print_descending",
                        "print_field_descending_price", "show", "history":
                    if (!commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    break;
                case "remove_by_id", "remove_any_by_price":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.ARGUMENT;
                case "add", "add_if_min":
                    if (!commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.OBJECT;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.SCRIPT;
                case "update":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.UPDATE;
                case "exit":
                    System.exit(0);
                default:
                    return CommandType.ERROR;
            }
        } catch (WrongAmountOfElementsException e) {
            ConsolePrinter.printError("Invalid command format");
            return CommandType.ERROR;
        }
        return CommandType.SIMPLE;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

}