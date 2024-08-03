package run;

import javafx.application.Application;
import javafx.stage.Stage;
import utilities.ConsoleManager;

public class Client extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start(primaryStage);
    }
}
