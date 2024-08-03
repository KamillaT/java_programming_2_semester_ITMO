package utilities;

import connector.Communicator;
import data.UserInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mode.UserInputMode;
import processing.CommandHandler;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleManager extends Application {
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;
    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static String currentUser;
    private static Locale locale = Locale.getDefault();

    private static ResourceBundle messages;

    @Override
    public void start(Stage primaryStage) {
        openRegistrationStage(Locale.getDefault()); // Open registration stage with default locale
    }

    private void openRegistrationStage(Locale locale) {
        Stage registrationStage = new Stage();

        messages = ResourceBundle.getBundle("messages", locale);
        registrationStage.setTitle(messages.getString("registration.title"));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ChoiceBox<String> languageChoiceBox = new ChoiceBox<>();
        languageChoiceBox.getItems().addAll("English", "Русский", "中文", "한국어");
        languageChoiceBox.setValue(getLanguageString(locale));

        Label loginLabel = new Label(messages.getString("registration.login"));
        TextField loginField = new TextField();
        Label passwordLabel = new Label(messages.getString("registration.password"));
        TextField passwordField = new TextField();
        Button registerButton = new Button(messages.getString("registration.register"));

        gridPane.add(loginLabel, 0, 0);
        gridPane.add(loginField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(registerButton, 1, 2);
        gridPane.add(languageChoiceBox, 0, 3);

        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setLocale(newValue);
            this.locale = setLocale(newValue);
            loginLabel.setText(messages.getString("registration.login"));
            passwordLabel.setText(messages.getString("registration.password"));
            registerButton.setText(messages.getString("registration.register"));
        });

        registerButton.setOnAction(event -> {
            String login = loginField.getText();
            String password = passwordField.getText();

            if (!UserInfo.isLoginValid(login) || !UserInfo.isPasswordValid(password)) {
                showAlert(messages.getString("registration.invalidInput"), messages.getString("registration.invalidInputMessage"));
                return;
            }

            CommandHandler commandHandler = establishConnection("localhost", 9876, login, password, locale);
            if (commandHandler != null) {
                registrationStage.close();
                currentUser = login;
                openUserInputStage(commandHandler, locale);
            }
        });

        Scene scene = new Scene(gridPane, 300, 150);
        registrationStage.setScene(scene);
        registrationStage.show();
    }

    private String getLanguageString(Locale locale) {
        switch (locale.getLanguage()) {
            case "en":
                return "English";
            case "ru":
                return "Русский";
            case "zh":
                return "中文";
            case "ko":
                return "한국어";
            default:
                return "English";
        }
    }

    private Locale setLocale(String language) {
        switch (language) {
            case "English":
                Locale.setDefault(new Locale("en", "GB"));
                messages = ResourceBundle.getBundle("messages", new Locale("en", "GB"));
                return new Locale("en", "GB");
            case "中文":
                Locale.setDefault(new Locale("zh", "CN"));
                messages = ResourceBundle.getBundle("messages", new Locale("zh", "CN"));
                return new Locale("zh", "CN");
            case "한국어":
                Locale.setDefault(new Locale("ko", "KR"));
                messages = ResourceBundle.getBundle("messages", new Locale("ko", "KR"));
                return new Locale("ko", "KR");
            default:
                Locale.setDefault(new Locale("ru", "RU"));
                messages = ResourceBundle.getBundle("messages", new Locale("ru", "RU"));
                return new Locale("ru", "RU");
        }
    }

    private void openUserInputStage(CommandHandler commandHandler, Locale locale) {
        Stage userInputStage = new Stage();
        userInputStage.setTitle("User Input");
        locale = this.locale;

        UserInputMode userInputMode = new UserInputMode(commandHandler, currentUser, locale);
        Scene userInputScene = new Scene(userInputMode.getRootNode(), 720, 480);
        userInputStage.setScene(userInputScene);
        userInputStage.show();
    }

    private CommandHandler establishConnection(String host, int port, String login, String password, Locale locale) {
        Communicator communicator = new Communicator(host,
                port,
                RECONNECTION_TIMEOUT,
                MAX_RECONNECTION_ATTEMPTS,
                login,
                password);

        CommandHandler commandHandler = new CommandHandler(communicator, locale);

        if (commandHandler.executeCommand(new String[]{"info", ""}) == -1) {
            showAlert(messages.getString("alert.title.unableToLogin"), messages.getString("alert.message.unableToLogin"));
            return null;
        }

        return commandHandler;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
