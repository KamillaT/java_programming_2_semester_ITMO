package commands;

import exceptions.WrongAmountOfElementsException;
import utility.ConsolePrinter;

import java.util.HashMap;
import java.util.Stack;

/**
 * The Invoker class is responsible for executing commands.
 * It contains a map of commands and their corresponding names.
 */
public class Invoker {
    private final static HashMap<String, AbstractCommand> commands = new HashMap<>();

    /**
     * Registers a command with its name in the commands map.
     *
     * @param name    the name of the command
     * @param command the command object
     */
    public static void register(String name, AbstractCommand command) {
        commands.put(name, command);
    }

    /**
     * Executes a command based on its name.
     *
     * @param name the name of the command to execute
     * @return 1 if the command is executed successfully, 0 otherwise
     */
    public static int executeCommand(String[] name) {
        try {
            if (name.length > 0) {
                AbstractCommand command = commands.get(name[0]);
                command.execute(name);
                return 1;
            }
            ConsolePrinter.printError("You have not entered the command !");

        } catch (IllegalStateException | NullPointerException exception) {
            if (!name[0].equals("") && (!name[0].equals("execute_script"))) {
                ConsolePrinter.printError("The command " + name[0] + " does not exist! Use command 'help' to get the available command list !");
            }
        } catch (WrongAmountOfElementsException exception) {
            ConsolePrinter.printError("Invalid command format!");
        }
        return 0;
    }

    /**
     * Retrieves the map of registered commands.
     *
     * @return the map of commands
     */
    public HashMap<String, AbstractCommand> getCommands() {
        return commands;
    }
}
