package run;


import utility.Console;
import utility.ConsolePrinter;
import utility.csv.CSVProcess;
import utility.mode.UserInputMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // ToDo add env var (done)
        // ToDo check permissions (done)
        // ToDo remove question on start (done)
        // ToDo add validation data in file with collection (done)
        // ToDo add recursion detection (done)
        String path;
        if (System.getenv("FILE_PATH") != null) {
            path = System.getenv("FILE_PATH");

            ConsolePrinter.printInformation("Welcome to our application!");
            ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
            Console.starter(path);
//            Console.starter("C:\\Users\\Kamilla\\IdeaProjects\\java_programming_2_semester-main-v.3\\java_programming_2_semester-main\\CollectionManager\\output\\data\\test.csv");
//            Console.starter("/home/studs/s409660/output/data/test.csv);
        }
        else {
            ConsolePrinter.printError("No path detected");
        }
    }
}
