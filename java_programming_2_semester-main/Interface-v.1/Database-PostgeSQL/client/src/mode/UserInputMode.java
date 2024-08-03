package mode;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import processing.CommandHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserInputMode implements IMode {
    private String[] commands = {"", ""};
    public static GridPane rootNode;
    private CommandHandler commandHandler;
    private String currentUser;
    public static TextArea commandHandlerTextArea = new TextArea();
    private Button collectionInfoButton;
    private MutableLocale locale;

    // Resource bundle for localized strings
    private ResourceBundle messages;

    /**
     * Constructs the user input mode interface.
     */
    public UserInputMode(CommandHandler commandHandler, String currentUser, Locale locale) {
        this.commandHandler = commandHandler;
        this.currentUser = currentUser;
        this.locale = new MutableLocale(locale);

        Label timeLabel = new Label();

        final Timeline[] timeline = {new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = createDateTimeFormatter(this.locale.getLocale());
                    String formattedTime = now.format(formatter);
                    timeLabel.setText(formattedTime);
                })
        )};
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();

        messages = ResourceBundle.getBundle("messages", locale);

        rootNode = new GridPane();
        rootNode.setPadding(new Insets(10));
        rootNode.setHgap(10);
        rootNode.setVgap(10);

        rootNode.add(timeLabel, 0, 5);

        ChoiceBox<String> languageChoiceBox = new ChoiceBox<>();
        languageChoiceBox.getItems().addAll("English", "Русский", "中文", "한국어");
        languageChoiceBox.setValue(getCurrentLanguage(locale));

        rootNode.add(languageChoiceBox, 0, 4);

        Label userLabel = new Label(messages.getString("label.user"));
        Label userNameLabel = new Label(currentUser);

        collectionInfoButton = new Button(messages.getString("button.collectionInfo"));
        Label commandLabel = new Label(messages.getString("label.command"));
        TextField commandField = new TextField();

        commandField.setOnAction(event -> {
            String[] userCommand = commandField.getText().trim().toLowerCase().split(" ");
            commands = userCommand.length > 1 ? userCommand : new String[]{userCommand[0], ""};
            executeCommand(commands);
            commandField.clear();
        });

        rootNode.add(userLabel, 0, 0);
        rootNode.add(userNameLabel, 1, 0);
        rootNode.addRow(2, collectionInfoButton);
        rootNode.add(commandLabel, 0, 1);
        rootNode.add(commandField, 1, 1);
        rootNode.add(commandHandlerTextArea, 1, 2);

        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String language = languageChoiceBox.getValue();
            this.locale.setLocale(getLocale(language));
            messages = ResourceBundle.getBundle("messages", this.locale.getLocale());
            commandHandler.setLocale(this.locale.getLocale());
            userLabel.setText(messages.getString("label.user"));
            collectionInfoButton.setText(messages.getString("button.collectionInfo"));
            commandLabel.setText(messages.getString("label.command"));

            timeline[0].stop();

            timeline[0] = new Timeline(
                    new KeyFrame(Duration.seconds(1), ev -> {
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = createDateTimeFormatter(this.locale.getLocale());
                        String formattedTime = now.format(formatter);
                        timeLabel.setText(formattedTime);
                    })
            );
            timeline[0].setCycleCount(Timeline.INDEFINITE);
            timeline[0].play();
        });

        try {
            commandHandler.showCollection();
        } catch (IOException | ClassNotFoundException e) {
            showAlert(messages.getString("alert.title.exception"), messages.getString("alert.message.exceptionOccurred"));
        }

        collectionInfoButton.setOnAction(event -> {
            commandHandler.executeCommand(new String[]{"info", ""});
        });

        Button canvasButton = new Button("Canvas");
        canvasButton.setOnAction(event -> {
            try {
                commandHandler.drawProducts();
            } catch (IOException | ClassNotFoundException e) {
                showAlert(messages.getString("alert.title.exception"), messages.getString("alert.message.exceptionOccurred"));
            }
        });

        rootNode.add(canvasButton, 1, 5);
    }

    private static String getCurrentLanguage(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "中文";
        } else if (locale.equals(Locale.KOREA)) {
            return "한국어";
        } else if (locale.equals(Locale.ENGLISH)) {
            return "English";
        }
        return "Русский";
    }

    private static class MutableLocale {
        private Locale locale;

        public MutableLocale(Locale locale) {
            this.locale = locale;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public Locale getLocale() {
            return locale;
        }
    }

    public DateTimeFormatter createDateTimeFormatter(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss", locale);
        } else if (locale.equals(Locale.KOREA)) {
            return DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss", locale);
        } else if (locale.equals(Locale.ENGLISH) || locale.equals(Locale.getDefault())) {
            return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss", locale);
        } else {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", locale);
        }
    }

    /**
     * Executes the provided command using the command handler.
     * @param commands The array containing the command and its arguments.
     */
    private void executeCommand(String[] commands) {
        // Execute the command using the command handler
        int commandStatus = commandHandler.executeCommand(commands);
        if (commandStatus == -1) {
            showAlert(messages.getString("alert.title.error"), messages.getString("alert.message.failedToExecuteCommand"));
        }
    }

    private Locale getLocale(String language) {
        switch (language) {
            case "English":
                return new Locale("en", "GB");
            case "Русский":
                return new Locale("ru", "RU");
            case "中文":
                return new Locale("zh", "CN");
            case "한국어":
                return new Locale("ko", "KR");
            default:
                return Locale.getDefault();
        }
    }

    /**
     * Returns the root node of the JavaFX interface.
     * @return The root node of the JavaFX interface.
     */
    public GridPane getRootNode() {
        return rootNode;
    }

    // Helper method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Executes the interactive mode for receiving user input and executing commands.
     */
    @Override
    public void executeMode(CommandHandler commandHandler) {

    }
}
