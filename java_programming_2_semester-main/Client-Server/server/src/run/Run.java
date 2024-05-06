package run;

import file.CSVProcess;
import server.Server;
import utilities.CollectionManager;

public class Run {
    public static void main(String[] args) {
//        String port = args[0];
//        String pathToFile = args[1];
//        String pathToFile = "C:\\Users\\Kamilla\\IdeaProjects\\java_programming_2_semester_lab5\\java_programming_2_semester-main\\Client-Server\\test\\test.csv";
        if (System.getenv("FILE_PATH") != null && System.getenv("SCRIPT_PATH") != null) {
            String port = "9876";
            String pathToFile = System.getenv("FILE_PATH");
            CSVProcess.setPathToFile(pathToFile);
            CollectionManager.getCollectionFromFile(pathToFile);
            Runtime.getRuntime().addShutdownHook(new Thread(CSVProcess::writeCollection));
            Server server = new Server(port);
            server.run();
        } else {
            if (System.getenv("FILE_PATH") == null) {
                System.out.println("Please configure the file path");
            }
            if (System.getenv("SCRIPT_PATH") == null) {
                System.out.println("Please configure the script folder path");
            }
            System.exit(0);
        }
    }
}