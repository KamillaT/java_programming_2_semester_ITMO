package utility.mode;

import commands.CommandManager;
import commands.Invoker;
import commands.Receiver;
import utility.ConsolePrinter;
import utility.creator.ProductBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**

 The FileScriptMode class represents a mode for executing commands from a script file.
 It reads commands from the script file and handles their execution.
 */
public class FileScriptMode implements IMode {
    private String argument;
    private boolean flag = false;
    private int count = 0;
    private int number = 0;
    private int commandStatus = 0;
    private final List<String> scriptStack = new ArrayList<>();
    private final Receiver receiver = new Receiver(new Invoker());
    private Scanner userScanner;

    /**
     * Constructs a FileScriptMode object with the specified script file argument.
     *
     * @param argument The path to the script file.
     */
    public FileScriptMode (String argument) {
        this.argument = argument;
    }
    /**
     * Executes the commands from the specified script file.
     */
    @Override
    public void executeMode() {
        count += 1;
        ProductBuilder productBuilder = new ProductBuilder(new Scanner(System.in));
        Scanner tmpScanner = productBuilder.getUserScanner();
        CommandManager.commandStarter();
        try {
            initializeScript(argument);
            productBuilder.setUserScanner(userScanner);
            productBuilder.setFileMode();
            do {
                String userCommand = getNextUserCommand();
                handleUserCommand(userCommand);
            } while (commandStatus == 1 && userScanner.hasNextLine());

        } catch (FileNotFoundException exception) {
            ConsolePrinter.printError("File not found!");
        } catch (NoSuchElementException exception) {
            ConsolePrinter.printError("The script file is empty!");
        } catch (IllegalStateException exception) {
            ConsolePrinter.printError("Unexpected error!");
        }
        productBuilder.setUserScanner(tmpScanner);
        productBuilder.setUserMode();
    }

    /**
     * Initializes the script by creating a scanner for reading input from the script file.
     *
     * @param argument The path to the script file.
     * @throws FileNotFoundException  if the script file is not found.
     * @throws NoSuchElementException if the script file is empty.
     */
    public void initializeScript (String argument) throws FileNotFoundException, NoSuchElementException {
        this.userScanner = new Scanner(new File(argument));
        if(!userScanner.hasNext()) throw new NoSuchElementException();
        scriptStack.add(argument.toLowerCase());
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
    public void handleUserCommand(String userCommand) {
        String[] commandPart = userCommand.split(" ",2);
        String commandName = commandPart[0].trim();
        String commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
        while (this.userScanner.hasNextLine() && commandName.isEmpty()) {
            commandPart = userCommand.split(" ",2);
            commandName = commandPart[0].trim();
            commandArgument = commandPart.length > 1 ? commandPart[1].trim() : "";
        }
        switch (commandName) {
            case "add" :
                receiver.add(this.userScanner);
                commandStatus = 1;
                break;
            case "update" :
                receiver.update(commandArgument, userScanner);
                commandStatus = 1;
                break;
            case "add_if_min" :
                receiver.addIfMin(userScanner);
                commandStatus = 1;
                break;
            case "execute_script" :
//                receiver.executeScript(commandArgument);
                handleExecuteScriptCommand(commandArgument);
                commandStatus = 1;
                break;
//            case "clear":
//                receiver.clear();
//                commandStatus = 1;
//                break;
//            case "exit":
//                receiver.exit();
//                commandStatus = 1;
//                break;
//            case "help":
//                receiver.help();
//                commandStatus = 1;
//                break;
//            case "history":
//                receiver.history();
//                commandStatus = 1;
//                break;
//            case "info":
//                receiver.info();
//                commandStatus = 1;
//                break;
//            case "print_descending":
//                receiver.descendingOrder();
//                commandStatus = 1;
//                break;
//            case "print_field_descending_price":
//                receiver.descendingPriceOrder();
//                commandStatus = 1;
//                break;
            case "remove_any_by_price":
                receiver.removeByPrice(commandArgument);
                commandStatus = 1;
                break;
            case "remove_by_id":
                receiver.removeById(commandArgument);
                commandStatus = 1;
                break;
//            case "remove_head":
//                receiver.removeHead();
//                commandStatus = 1;
//                break;
//            case "save":
//                receiver.save();
//                commandStatus = 1;
//                break;
//            case "show":
//                receiver.show();
//                commandStatus = 1;
//                break;
            default :
                commandStatus = Invoker.executeCommand(commandPart);
        }
    }

    /**
     * Handles the execution of an execute_script command.
     *
     * @param scriptFile The path to the script file to be executed.
     */
    public void handleExecuteScriptCommand (String scriptFile) {
//        System.out.println(scriptStack);
//        String path = null;
//        if (System.getenv("HOMEPATH") != null) {
//            path = System.getenv("HOMEPATH") + "\\";
//        } else if (System.getenv("HOME") != null) {
//            path = System.getenv("HOME") + "/";
//        }
//        String fullScriptPath = path + scriptFile;
        for (String script : scriptStack) {
            if (scriptFile.toLowerCase().equals(script)) {
                flag = true;
//                if (this.flag) {
//                    askAction();
//                }
//                this.flag = false;
//                while(count <= number) {
//                    ConsolePrinter.printResult("The execute_script command has been execute " + count + " - time!");
//                    scriptStack.remove(scriptStack.size() - 1);
//                    this.executeMode();
//                }
                break;
            }
        }
        if (flag) {
            ConsolePrinter.printError("Detected recursion! Script won't run");
            scriptStack.clear();
        }
        else {
            argument = scriptFile;
            this.executeMode();
        }
    }

    /**
     * Asks the user for the number of times to execute the execute_script command.
     */
    public void askTimes () {
        while (true) {
            ConsolePrinter.printResult("How many times do you want to execute this command?");
            Scanner scanner2 = new Scanner(System.in);
            String scn2 = scanner2.nextLine().trim();
            try {
                this.number = Integer.parseInt(scn2);
                break;
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("Please enter a valid number!");
            }
        }
    }

    /**
     * Asks the user whether to execute the execute_script command.
     */
    public void askAction() {
        ConsolePrinter.printResult("The execute_script command appears in the script file!");
        ConsolePrinter.printResult("Do you want to execute this command? (yes / no)");
        Scanner scanner1 = new Scanner(System.in);
        String scn1 = scanner1.nextLine().trim().toLowerCase();
        while(true) {
            if (scn1.equals("yes")) {
                this.askTimes();
                break;
            } else if (scn1.equals("no")) {
                break;
            } else {
                ConsolePrinter.printError("Invalid input. Please enter 'yes' or 'no'!");
                break;
            }
        }
    }

    /**
     * Retrieves the scanner used for reading input from the script file.
     *
     * @return The scanner used for reading input from the script file.
     */
    @Override
    public Scanner getScanner() {
        return userScanner;
    }

}
