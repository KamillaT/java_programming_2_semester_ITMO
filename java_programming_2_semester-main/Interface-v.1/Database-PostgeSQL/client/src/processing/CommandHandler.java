package processing;

import connector.Communicator;
import connector.Receiver;
import connector.Sender;
import exceptions.WrongAmountOfElementsException;
import interaction.Request;
import interaction.Response;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import mode.FileScriptMode;
import mode.UserInputMode;
import utilities.ColorGenerator;
import utilities.CommandType;
import utility.ProductCreator;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CommandHandler {
    private static Communicator communicator;
    private Scanner scanner = new Scanner(System.in);
    private Stage stage = new Stage();
    private TextArea resultTextArea = UserInputMode.commandHandlerTextArea;
    private boolean fileMode = false;
    private static Locale locale;
    private static Map<String, Rectangle> productRectangles;
    private static Map<String, ProductData> productDataHashMap;
    private static Canvas canvas = new Canvas(720, 480);
    private static GraphicsContext gc = canvas.getGraphicsContext2D();
    private static Stage canvasStage = new Stage();
    private static String result;

    private ResourceBundle messages;

    {
        this.resultTextArea.setEditable(false);
    }

    public CommandHandler(Communicator communicator, Locale locale) {
        this.communicator = communicator;
        this.messages = ResourceBundle.getBundle("messages", locale);
        this.locale = locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        this.messages = ResourceBundle.getBundle("messages", locale);
        try {
            resultTextArea.setText(messages.getString(result));
        } catch (MissingResourceException e) {
            resultTextArea.setText(result);
        }
    }

    public int executeCommand(String[] commandSet) {
        CommandType commandType = checkCommand(commandSet[0], commandSet[1]);
        try {
            if (commandType.equals(CommandType.SIMPLE)) {
                Request commandRequest = new Request(
                        commandSet[0],
                        communicator.getLogin(),
                        communicator.getPassword(),
                        locale
                );

                result = processCommand(commandRequest);
                // used to check login and password
                if (result.equals("Unable to login to database!")){
                    return -1;
                }
                try {
                    resultTextArea.clear();
                    String[] commands = result.split("\n");
                    if (commandSet[0].equals("help")) {
                        String delimeter = " \\(|\\)";
                        int i = 0;
                        for (String command : commands) {
                            String[] commandParts = command.split(delimeter);
                            String[] propertyParts = {"add.command", "remove.head.command", "add.min.command",
                            "execute.script.command", "show.command", "clear.command", "update.id.command",
                            "remove.id.command", "history.command", "help.command", "exit.command",
                            "descending.order.command", "remove.price.command", "descending.price.command",
                            "info.command"};
                            resultTextArea.appendText(commandParts[0] + ": " + messages.getString(propertyParts[i]) + "\n");
                            i ++;
                        }
                    } else if (commandSet[0].equals("info")) {
                        String delimeter = ": ";
                        int i = 0;
                        for (String command : commands) {
                            String[] commandPart = command.split(delimeter);
                            String[] propertyParts = {"collection.type", "init.date", "elem.num"};
                            resultTextArea.appendText(messages.getString(propertyParts[i]) + commandPart[1] + "\n");
                            i ++;
                        }
                    } else {
                        String text = messages.getString(result);
                        resultTextArea.setText(text);
                    }
                } catch (MissingResourceException exception) {
                    resultTextArea.setText(result);
                }
//                showCollection();
                return 1;
            }

            if (commandType.equals(CommandType.OBJECT)) {
                Request commandRequest = new Request(
                        commandSet[0],
                        ProductCreator.productCreator(stage, scanner, fileMode, locale),
                        communicator.getLogin(),
                        communicator.getPassword(),
                        locale
                );
                String result = processCommand(commandRequest);
                try {
                    String text = messages.getString(result);
                    resultTextArea.setText(text);
                } catch (MissingResourceException exception) {
                    resultTextArea.setText(result);
                }
//                showCollection();
                return 1;
            }

            if (commandType.equals(CommandType.ARGUMENT)) {
                Request commandRequest = new Request(
                        commandSet[0],
                        commandSet[1],
                        communicator.getLogin(),
                        communicator.getPassword(),
                        locale
                );
                result = processCommand(commandRequest);
                try {
                    String text = messages.getString(result);
                    resultTextArea.setText(text);
                } catch (MissingResourceException exception) {
                    resultTextArea.setText(result);
                }
//                showCollection();
                return 1;
            }

            if (commandType.equals(CommandType.UPDATE)) {
                Request commandRequest = new Request(
                        commandSet[0],
                        ProductCreator.productCreator(stage, scanner, fileMode, locale),
                        commandSet[1],
                        communicator.getLogin(),
                        communicator.getPassword(),
                        locale
                );
                result = processCommand(commandRequest);
                try {
                    String text = messages.getString(result);
                    resultTextArea.setText(text);
                } catch (MissingResourceException exception) {
                    resultTextArea.setText(result);
                }
//                showCollection();
                return 1;
            }

            if (commandType.equals(CommandType.SCRIPT)) {
                fileMode = true;
                FileScriptMode fileScriptMode = new FileScriptMode(commandSet[1]);
                fileScriptMode.executeMode(this);
                if (fileScriptMode.isExecutionComplete()) {
                    // Reset fileMode to false after script execution
                    fileMode = false;
                }
//                showCollection();
                return 1;
            }
            String command = commandSet[0]; // Assuming commandSet is an array containing command arguments
            String message = ResourceBundle.getBundle("messages", locale).getString("command.notExist");
            String formattedMessage = MessageFormat.format(message, command);
            showAlert(messages.getString("alert.title.error"), formattedMessage);
            return 0;
        } catch (IOException | ClassNotFoundException e) {
            showAlert(messages.getString("alert.title.exception"), messages.getString("alert.message.exceptionOccurred"));
        }

        return 0;
    }

    private static ProductData getProductData(String s) {
        ProductData productData = new ProductData(
                s.split("\n")[0].split(": ")[1],
                s.split("\n")[1].split(": ")[1],
                s.split("\n")[2].split(": ")[1].split(", ")[0].split(" = ")[1],
                s.split("\n")[2].split(": ")[1].split(", ")[1].split(" = ")[1],
                s.split("\n")[3].split(": ")[1],
                s.split("\n")[4].split(": ")[1],
                s.split("\n")[5].split(": ")[1],
                s.split("\n")[8].split(": ")[1],
                s.split("\n")[9].split(": ")[1],
                s.split("\n")[10].split(": ")[1],
                s.split("\n")[12].split(": ")[1],
                s.split("\n")[13].split(": ")[1],
                s.split("\n")[14].split(": ")[1]);
        return productData;
    }

    public void drawProducts() throws IOException, ClassNotFoundException {
        canvasStage.setTitle("Canvas Window");
        StackPane root = new StackPane();

        try {
            Request commandRequest = new Request(
                    "show",
                    communicator.getLogin(),
                    communicator.getPassword(),
                    locale
            );
            String[] result = processCommand(commandRequest).split("\n\n");
            productRectangles = new HashMap<>();
            productDataHashMap = new HashMap<>();
            for (String s : result) {
                ProductData productData = getProductData(s);
                double x = Double.parseDouble(productData.getProductCoordinateX());
                double y = Double.parseDouble(productData.getProductCoordinateY());
                double width = 40; // Ширина прямоугольника
                double height = 40; // Высота прямоугольника
                Color color = ColorGenerator.generateColor(productData.getCreatorID()); // Цвет прямоугольника (можете настроить по вашему вкусу)

                Rectangle rectangle = new Rectangle(x, y, width, height);
                rectangle.setFill(color);
                productRectangles.put(productData.getProductID(), rectangle);
                productDataHashMap.put(productData.getProductID(), productData);
                gc.setFill(rectangle.getFill());
                gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            }

            canvas.setOnMouseClicked(event -> {
                double mouseX = event.getX();
                double mouseY = event.getY();
                System.out.println("Clicked at: (" + mouseX + ", " + mouseY + ")");
                for (Map.Entry<String, Rectangle> entry : productRectangles.entrySet()) {
                    Rectangle rectangle = entry.getValue();
                    if (rectangle.contains(mouseX, mouseY)) {
                        // Обработка события при нажатии на прямоугольник
                        // Например, показ информации о продукте по ID
                        Stage primaryStage = new Stage();
                        String productID = entry.getKey();
                        productDataHashMap.get(productID).setStage(primaryStage);
//                        showAlert("Product Info", "Product ID: " + productID);
                        break; // Выходим из цикла, чтобы не обрабатывать клики на других прямоугольниках
                    }
//                    else {
//                        String[] commandSet = {"add", ""};
//                        executeCommand(commandSet);
//                        try {
//                            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//                            drawProducts();
//                        } catch (IOException | ClassNotFoundException e) {
//                            System.out.println("kkkkkkkkkkk");
//                        }
//                        break;
//                    }
                }
            });
        } catch (IOException | ClassNotFoundException exception) {
            showAlert(messages.getString("alert.title.exception"), messages.getString("alert.message.exceptionOccurred"));
        }

        root.getChildren().clear();
        root.getChildren().add(canvas);
        canvasStage.setScene(new Scene(root, 720, 480));
        canvasStage.show();
    }

    public void showCollection() throws IOException, ClassNotFoundException {
        try {
            Request commandRequest = new Request(
                    "show",
                    communicator.getLogin(),
                    communicator.getPassword(),
                    locale
            );

            ObservableList<ProductData> productDataObservableList = FXCollections.observableArrayList();
            String[] result = processCommand(commandRequest).split("\n\n");
            for (String s : result) {
                ProductData productData = getProductData(s);
                productDataObservableList.add(productData); // Add each ProductData object to the observable list
            }

            TableColumn<ProductData, String> productNameColumn = new TableColumn<>("Product Name");
            productNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductName()));

            TableColumn<ProductData, Double> productCoordinateXColumn = new TableColumn<>("Product Coordinate X");
            productCoordinateXColumn.setCellValueFactory(data -> new SimpleDoubleProperty(Double.parseDouble(data.getValue().getProductCoordinateX())).asObject());

            TableColumn<ProductData, Double> productCoordinateYColumn = new TableColumn<>("Product Coordinate Y");
            productCoordinateYColumn.setCellValueFactory(data -> new SimpleDoubleProperty(Double.parseDouble(data.getValue().getProductCoordinateY())).asObject());

            TableColumn<ProductData, String> productCreationDateColumn = new TableColumn<>("Product Creation Date");
            productCreationDateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProductCreationDate()));

            TableColumn<ProductData, Double> productPriceColumn = new TableColumn<>("Product Price");
            productPriceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(Double.parseDouble(data.getValue().getProductPrice())).asObject());

            TableColumn<ProductData, String> unitOfMeasureColumn = new TableColumn<>("Unit of Measure");
            unitOfMeasureColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnitOfMeasure()));

            TableColumn<ProductData, String> organizationNameColumn = new TableColumn<>("Organization Name");
            organizationNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrganizationName()));

            TableColumn<ProductData, Double> organizationAnnualTurnoverColumn = new TableColumn<>("Organization Annual Turnover");
            organizationAnnualTurnoverColumn.setCellValueFactory(data -> new SimpleDoubleProperty(Double
                    .parseDouble(data.getValue().getOrganizationAnnualTurnover())).asObject());

            TableColumn<ProductData, String> organizationTypeColumn = new TableColumn<>("Organization Type");
            organizationTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrganizationType()));

            TableColumn<ProductData, String> organizationStreetColumn = new TableColumn<>("Organization Street");
            organizationStreetColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrganizationStreet()));

            TableColumn<ProductData, String> organizationZipcodeColumn = new TableColumn<>("Organization Zipcode");
            organizationZipcodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOrganizationZipcode()));

            TableColumn<ProductData, Integer> creatorIDColumn = new TableColumn<>("Creator ID");
            creatorIDColumn.setCellValueFactory(data -> new SimpleIntegerProperty(Integer.parseInt(data.getValue().getCreatorID())).asObject());

            TableView<ProductData> tableView = new TableView<>(productDataObservableList);
            tableView.getColumns().addAll(
                    productNameColumn,
                    productCoordinateXColumn,
                    productCoordinateYColumn,
                    productCreationDateColumn,
                    productPriceColumn,
                    unitOfMeasureColumn,
                    organizationNameColumn,
                    organizationAnnualTurnoverColumn,
                    organizationTypeColumn,
                    organizationStreetColumn,
                    organizationZipcodeColumn,
                    creatorIDColumn
            );

            UserInputMode.rootNode.add(tableView, 1, 7);

        } catch (IOException | ClassNotFoundException exception) {
            showAlert(messages.getString("alert.title.exception"), messages.getString("alert.message.exceptionOccurred"));
        }
    }

    private String processCommand(Request request) throws IOException, ClassNotFoundException {
        communicator.connect();
        Sender sender = new Sender(communicator.getSocketChannel().socket());
        sender.sendObject(request);
        Receiver receiver = new Receiver(communicator.getSocketChannel().socket());
        Response response = receiver.receive();
        String answer = response.getAnswer();
        communicator.closeConnection();
        return answer;
    }

    private CommandType checkCommand(String command, String commandArgument) {
        try {
            switch (command.toLowerCase()) {
                case "help", "clear", "remove_head", "info", "print_descending",
                     "print_field_descending_price", "show", "history":
                    if (!commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    break;
                case "remove_by_id", "remove_any_by_price":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.ARGUMENT;
                case "add", "add_if_min":
                    if (!commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.OBJECT;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.SCRIPT;
                case "update":
                    if (commandArgument.isEmpty()) throw new WrongAmountOfElementsException();
                    return CommandType.UPDATE;
                case "exit":
                    System.exit(0);
                default:
                    return CommandType.ERROR;
            }
        } catch (WrongAmountOfElementsException e) {
            showAlert(messages.getString("alert.title.error"), messages.getString("alert.message.invalidCommandFormat"));
            return CommandType.ERROR;
        }
        return CommandType.SIMPLE;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    static class ProductData {
        private String productID;
        private String productName;
        private String productCoordinateX;
        private String productCoordinateY;
        private String productCreationDate;
        private String productPrice;
        private String unitOfMeasure;
        private String organizationName;
        private String organizationAnnualTurnover;
        private String organizationType;
        private String organizationStreet;
        private String organizationZipcode;
        private String creatorID;

        public ProductData(String productID, String productName, String productCoordinateX, String productCoordinateY,
                           String productCreationDate, String productPrice, String unitOfMeasure,
                           String organizationName, String organizationAnnualTurnover, String organizationType,
                           String organizationStreet, String organizationZipcode, String creatorID) {
            this.productID = productID;
            this.productName = productName;
            this.productCoordinateX = productCoordinateX;
            this.productCoordinateY = productCoordinateY;
            this.productCreationDate = productCreationDate;
            this.productPrice = productPrice;
            this.unitOfMeasure = unitOfMeasure;
            this.organizationName = organizationName;
            this.organizationAnnualTurnover = organizationAnnualTurnover;
            this.organizationType = organizationType;
            this.organizationStreet = organizationStreet;
            this.organizationZipcode = organizationZipcode;
            this.creatorID = creatorID;
        }

        public String getProductID() {
            return productID;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductCoordinateX() {
            return productCoordinateX;
        }

        public String getProductCoordinateY() {
            return productCoordinateY;
        }

        public String getProductCreationDate() {
            return productCreationDate;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public String getUnitOfMeasure() {
            return unitOfMeasure;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public String getOrganizationAnnualTurnover() {
            return organizationAnnualTurnover;
        }

        public String getOrganizationType() {
            return organizationType;
        }

        public String getOrganizationStreet() {
            return organizationStreet;
        }

        public String getOrganizationZipcode() {
            return organizationZipcode;
        }

        public String getCreatorID() {
            return creatorID;
        }

        public static String getProductName(ProductData productData) {
            return productData.productName;
        }

        public void setStage(Stage primaryStage) {
            primaryStage.setTitle("Rectangle Info");

            Label idLabel = new Label("Product ID: " + productID);
            Label nameLabel = new Label("Product Name: " + productName);
            Label coordinateXLabel = new Label("Product Coordinate X: " + productCoordinateX);
            Label coordinateYLabel = new Label("Product Coordinate Y: " + productCoordinateY);
            Label creationDateLabel = new Label("Product Creation Date: " + productCreationDate);
            Label priceLabel = new Label("Product Price: " + productPrice);
            Label unitOfMeasureLabel = new Label("Unit Of Measure: " + unitOfMeasure);
            Label organizationNameLabel = new Label("Organization Name: " + organizationName);
            Label organizationAnnualTurnoverLabel = new Label("Annual Turnover: " + organizationAnnualTurnover);
            Label organizationTypeLabel = new Label("Organization Type: " + organizationType);
            Label organizationStreetLabel = new Label("Organization Street: " + organizationStreet);
            Label organizationZipcodeLabel = new Label("Organization Zipcode: " + organizationZipcode);
            Label creatorIDLabel = new Label("Creator ID: " + creatorID);

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> primaryStage.close());

            Button deleteButton = new Button("Delete");

            CommandHandler commandHandler = new CommandHandler(communicator, locale);

            deleteButton.setOnAction(e -> {
                String[] commandSet = {"remove_by_id", productID};
                if (commandHandler.executeCommand(commandSet) == 1) {
                    try {
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        commandHandler.drawProducts();
                    } catch (IOException | ClassNotFoundException exception) {
                        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                    }
//                    Rectangle rectangle = productRectangles.get(productID);
//                    productDataHashMap.remove(productID);
//                    gc.clearRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
                }
                primaryStage.close();
            });

            Button updateButton = new Button("Update");
            updateButton.setOnAction(e -> {
                String[] commandSet = {"update", productID};
                if (commandHandler.executeCommand(commandSet) == 1) {
                    try {
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        commandHandler.drawProducts();
                    } catch (IOException | ClassNotFoundException exception) {
                        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                    }
//                    productDataHashMap.remove(oldProductData);
//                    gc.clearRect(oldRectangle.getX(), oldRectangle.getY(), oldRectangle.getWidth(), oldRectangle.getHeight());
                }
//                commandHandler.resetCanvasUpdateRequest();
                primaryStage.close();
            });

            VBox layout = new VBox(10);
            layout.getChildren().addAll(
                    idLabel,
                    nameLabel,
                    coordinateXLabel,
                    coordinateYLabel,
                    creationDateLabel,
                    priceLabel,
                    unitOfMeasureLabel,
                    organizationNameLabel,
                    organizationAnnualTurnoverLabel,
                    organizationTypeLabel,
                    organizationStreetLabel,
                    organizationZipcodeLabel,
                    creatorIDLabel,
                    closeButton,
                    deleteButton,
                    updateButton
            );

            Scene scene = new Scene(layout, 500, 600);
            primaryStage.setScene(scene);

            primaryStage.initModality(Modality.APPLICATION_MODAL); // Установка модального режима
            primaryStage.showAndWait(); // Отображение окна и ожидание его закрытия
        }
    }

}