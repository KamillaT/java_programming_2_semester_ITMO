package run;


import utility.Console;
import utility.ConsolePrinter;
import utility.csv.CSVProcess;
import utility.mode.UserInputMode;

public class App {
    public static void main(String[] args) {
        ConsolePrinter.printInformation("Welcome to our application!");
        ConsolePrinter.printInformation("You need help ? Use command 'help' to get the available command list!");
        Console.starter("C:\\Users\\Kamilla\\IdeaProjects\\java_programming_2_semester-main-v.3\\java_programming_2_semester-main\\CollectionManager\\output\\data\\test.csv");
    }
}
