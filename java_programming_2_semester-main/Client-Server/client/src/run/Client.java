package run;

import utilities.ConsoleManager;
import utility.ConsolePrinter;

public class Client {
    public static void main(String[] args) {
//        ConsoleManager.interactive(args[0], args[1]);
        if (System.getenv("FILE_PATH") != null && System.getenv("SCRIPT_PATH") != null) {
            ConsoleManager.interactive("localhost", "9876");
        } else {
            if (System.getenv("FILE_PATH") == null) {
                ConsolePrinter.printError("Please configure the file path");
            }
            if (System.getenv("SCRIPT_PATH") == null) {
                ConsolePrinter.printError("Please configure the script folder path");
            }
            System.exit(0);
        }
    }
}