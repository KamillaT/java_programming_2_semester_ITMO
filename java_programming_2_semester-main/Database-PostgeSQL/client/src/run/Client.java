package run;

import utilities.ConsoleManager;

public class Client {
    public static void main(String[] args) {
        ConsoleManager.interactive("localhost", "9876");
    }
}