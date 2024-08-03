package mode;

import exceptions.ScriptRecursionException;
import javafx.scene.control.Alert;
import processing.CommandHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileScriptMode implements IMode {
    private final String argument;
    private int commandStatus = 0;
    private final List<String> scriptStack = new ArrayList<>();
    private Scanner userScanner;
    public FileScriptMode(String argument) {
        this.argument = argument;
    }
    private boolean executionComplete;

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Executes the commands from the specified script file.
     */
    @Override
    public void executeMode(CommandHandler commandHandler) {
        try {
            initializeScript(this.argument);
            commandHandler.setScanner(userScanner);
            do {
                String userCommand = getNextUserCommand();
                commandStatus = handleUserCommand(userCommand, commandHandler);
            } while (commandStatus == 1 && userScanner.hasNextLine());
            executionComplete = true;
        } catch (FileNotFoundException exception) {
            showErrorAlert("Find not found!");
        } catch (NoSuchElementException exception) {
            showErrorAlert("The script file is empty!");
        } catch (IllegalStateException exception) {
            showErrorAlert("Unexpected error!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ScriptRecursionException e) {
            showErrorAlert("The script cannot be executed recursively");
        }
    }

    /**
     * Initializes the script by creating a scanner for reading input from the script file.
     *
     * @param argument The path to the script file.
     * @throws FileNotFoundException  if the script file is not found.
     * @throws NoSuchElementException if the script file is empty.
     */
    public void initializeScript(String argument) throws FileNotFoundException, NoSuchElementException {
        String path = System.getenv("SCRIPT_PATH") + argument;
        this.userScanner = new Scanner(new File(path));
        if (!userScanner.hasNext()) throw new NoSuchElementException();
        scriptStack.add(path);
    }

    /**
     * Retrieves the next user command from the script file.
     *
     * @return The next user command.
     */
    public String getNextUserCommand() {
        String userCommand = this.userScanner.nextLine().trim();
//        ConsolePrinter.printInformation(">" + userCommand);
        return userCommand;
    }

    /**
     * Handles the execution of a user command.
     *
     * @param userCommand The user command to be executed.
     */
    public int handleUserCommand(String userCommand, CommandHandler commandHandler) throws IOException, ScriptRecursionException {
        String[] commandPart = userCommand.split(" ").length > 1 ? userCommand.split(" ") : new String[]{userCommand.split(" ")[0], ""};
        String commandName = commandPart[0].trim();
        String commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
        while (this.userScanner.hasNextLine() && commandName.isEmpty()) {
            commandPart = userCommand.split(" ", 2);
            commandName = commandPart[0].trim();
            commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
        }
        if (commandName.equals("execute_script") ) {
            for (String script : scriptStack) {
                if (script.equals(commandArgument)) throw new ScriptRecursionException();
            }
            commandHandler.executeCommand(commandPart);
            return 1;
        }
        commandHandler.executeCommand(commandPart);
        return 1;
    }

    // Method to indicate that script execution is complete
    public void executionComplete() {
        this.executionComplete = true;
    }

    // Method to check if script execution is complete
    public boolean isExecutionComplete() {
        return executionComplete;
    }

}