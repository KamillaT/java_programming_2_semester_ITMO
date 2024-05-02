package run;

import utilities.ConsoleManager;

public class Client {
    public static void main(String[] args) {
//        ConsoleManager.interactive(args[0], args[1]);
        ConsoleManager.interactive("localhost", "9876");
    }
}