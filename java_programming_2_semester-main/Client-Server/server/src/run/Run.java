package run;

import file.CSVProcess;
import server.Server;
import utilities.CollectionManager;

public class Run {
    public static void main(String[] args) {
//        String port = args[0];
//        String pathToFile = args[1];
        String pathToFile = "C:\\Users\\User\\IdeaProjects\\lab5\\lab5\\java_programming_2_semester-main\\Client-Server\\test\\test.csv";
        String port = "9876";
        CSVProcess.setPathToFile(pathToFile);
        CollectionManager.getCollectionFromFile(pathToFile);
        Runtime.getRuntime().addShutdownHook(new Thread(CSVProcess::writeCollection));
        Server server = new Server(port);
        server.run();
    }
}