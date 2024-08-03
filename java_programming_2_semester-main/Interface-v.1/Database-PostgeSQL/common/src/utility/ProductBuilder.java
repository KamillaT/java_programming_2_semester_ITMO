package utility;

import data.*;
import exceptions.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Utility class for getting all information about the organization from the user
 */
public class ProductBuilder {

    private Scanner userScanner;
    private boolean fileMode;
    private Locale locale;
    private Stage stage;
    private GridPane root = new GridPane();
    private Scene scene = new Scene(root, 400, 500);

    private TextField nameField = new TextField();
    private TextField xField = new TextField();
    private TextField yField = new TextField();
    private TextField priceField = new TextField();
    private TextField orgNameField = new TextField();
    private TextField annualTurnoverField = new TextField();
    private TextField streetField = new TextField();
    private TextField zipCodeField = new TextField();

    private ResourceBundle messages;

    ComboBox<UnitOfMeasure> unitComboBox = new ComboBox<>();
    ComboBox<OrganizationType> orgTypeComboBox = new ComboBox<>();

    public static Button submitButton = new Button("Submit");

    public ProductBuilder(Stage primaryStage, Scanner userScanner,  boolean fileMode, Locale locale) {
        this.stage = primaryStage;
        this.userScanner = userScanner;
        this.fileMode = fileMode;
        this.locale = locale;
        this.messages = ResourceBundle.getBundle("messages", locale);
    }

    public void setUserScanner(Scanner scanner) {
        this.userScanner = scanner;
    }
    public Scanner getUserScanner() {
        return userScanner;
    }

    public void setFileMode() {
        this.fileMode = true;
    }
    public void setUserMode() {
        this.fileMode = false;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String nameAsker() throws WrongInputInScriptException {
        String name = "";
        if (fileMode) {
            while (true) {
                try {
                    name = userScanner.nextLine().trim(); // bug somewhere here
                    if (name.equals("")) throw new NotDeclaredValueException();
                    break;
                } catch (NotDeclaredValueException exception) {
                    showErrorAlert(messages.getString("alert.name.empty"));
                    throw new WrongInputInScriptException();
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.name.not.recognized"));
                    throw new WrongInputInScriptException();
                } catch (IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                }
            }
        }
        else {
            try {
                name = nameField.getText().trim();
                if (name.equals("")) throw new NotDeclaredValueException();
            } catch (NotDeclaredValueException exception) {
                showErrorAlert(messages.getString("alert.name.empty"));
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.name.not.recognized"));
                System.exit(0); // check here
            } catch (IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            }
        }
        return name;
    }

    public Double xAsker() throws WrongInputInScriptException {
        String strX;
        double x = 0.0;
        if (fileMode) {
            while (true) {
                try {
                    strX = userScanner.nextLine().trim();
                    x = Double.parseDouble(strX);
                    if(strX.isEmpty()) throw new NotDeclaredValueException();
                    break;
                } catch (NotDeclaredValueException exception) {
                    showErrorAlert(messages.getString("alert.x.empty"));
                    throw new WrongInputInScriptException();
                } catch (NumberFormatException exception) {
                    showErrorAlert(messages.getString("alert.x.not.number"));
                    throw new WrongInputInScriptException();
                } catch (NullPointerException | IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.x.not.recognized"));
                    throw new WrongInputInScriptException();
                }
            }
        }
        else {
            try {
                strX = xField.getText().trim();
                x = Double.parseDouble(strX);
                if (strX.isEmpty()) throw new NotDeclaredValueException();

            } catch (NotDeclaredValueException exception) {
                showErrorAlert(messages.getString("alert.x.empty"));
            } catch (NumberFormatException exception) {
                showErrorAlert(messages.getString("alert.x.not.number"));
            } catch (NullPointerException | IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.x.not.recognized"));
                System.exit(0);
            }
        }
        return x;
    }

    public Double yAsker() throws WrongInputInScriptException {
        String strY;
        double y= 0.0;
        if (fileMode) {
            while (true) {
                try {
                    strY = userScanner.nextLine().trim();
                    y = Double.parseDouble(strY);
                    if(strY.isEmpty()) throw new NotDeclaredValueException();
                    break;
                } catch (NotDeclaredValueException exception) {
                    showErrorAlert(messages.getString("alert.y.empty"));
                    throw new WrongInputInScriptException();
                } catch (NumberFormatException exception) {
                    showErrorAlert(messages.getString("alert.y.not.number"));
                    throw new WrongInputInScriptException();
                } catch (NullPointerException | IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.y.not.recognized"));
                    throw new WrongInputInScriptException();
                }
            }
        }
        else {
            try {
                strY = xField.getText().trim();
                y = Double.parseDouble(strY);
                if (strY.isEmpty()) throw new NotDeclaredValueException();
            } catch (NotDeclaredValueException exception) {
                showErrorAlert(messages.getString("alert.y.empty"));
            } catch (NumberFormatException exception) {
                showErrorAlert(messages.getString("alert.y.not.number"));
            } catch (NullPointerException | IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.y.not.recognized"));
                System.exit(0);
            }
        }
        return y;
    }

    public Float priceAsker() throws WrongInputInScriptException {
        String strprice;
        float price = 0.0f;
        if (fileMode) {
            while (true) {
                try {
                    strprice = userScanner.nextLine().trim();
                    price = Float.parseFloat(strprice);
                    if (price <= 0) throw new NotInDeclaredLimitsException();
                    break;
                } catch (NotInDeclaredLimitsException exception) {
                    showErrorAlert(messages.getString("alert.price.zero"));
                    throw new WrongInputInScriptException();
                } catch (NumberFormatException exception) {
                    showErrorAlert(messages.getString("alert.price.not.number"));
                    throw new WrongInputInScriptException();
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.price.not.recognized"));
                    throw new WrongInputInScriptException();
                } catch (NullPointerException | IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                }
            }
        }
        else {
            try {
                strprice = priceField.getText().trim();
                price = Float.parseFloat(strprice);
                if (price <= 0) throw new NotInDeclaredLimitsException();
            } catch (NotInDeclaredLimitsException exception) {
                showErrorAlert(messages.getString("alert.price.zero"));
            } catch (NumberFormatException exception) {
                showErrorAlert(messages.getString("alert.price.not.number"));
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.price.not.recognized"));
                System.exit(0);
            } catch (NullPointerException | IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            }
        }
        return price;
    }

    public UnitOfMeasure unitOfMeasureAsker() throws WrongInputInScriptException {
        UnitOfMeasure unitOfMeasure = null;
        String strUnitOfMeasure;
        if (fileMode) {
            while (true) {
                try {
                    strUnitOfMeasure = userScanner.nextLine().trim();
                    unitOfMeasure = UnitOfMeasure.valueOf(strUnitOfMeasure.toUpperCase());
                    break;
                } catch (IllegalArgumentException exception) {
                    showErrorAlert(messages.getString("alert.type.not.exist"));
                    throw new WrongInputInScriptException();
                } catch (IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.type.not.recognized"));
                    throw new WrongInputInScriptException();
                }
            }
        }
        else {
            try {
                unitOfMeasure = unitComboBox.getSelectionModel().getSelectedItem();

            } catch (IllegalArgumentException exception) {
                showErrorAlert(messages.getString("alert.type.not.exist"));
            } catch (IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.type.not.recognized"));
                System.exit(0);
            }
        }
        return unitOfMeasure;
    }

    public String organizationNameAsker() throws WrongInputInScriptException {
        String name = "";
        if (fileMode) {
            while (true) {
                try {
                    name = userScanner.nextLine().trim();
                    if (name.equals("")) throw new NotDeclaredValueException();
                    break;
                } catch (NotDeclaredValueException exception) {
                    showErrorAlert(messages.getString("alert.name.empty"));
                    throw new WrongInputInScriptException();
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.name.not.recognized"));
                    System.exit(0);
                } catch (IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                }
            }
        }
        else {
            try {
                name = orgNameField.getText().trim();
                if (name.equals("")) throw new NotDeclaredValueException();
            } catch (NotDeclaredValueException exception) {
                showErrorAlert(messages.getString("alert.name.empty"));
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.name.not.recognized"));
                System.exit(0);
            } catch (IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            }
        }
        return name;
    }

    public Double annualTurnoverAsker() throws WrongInputInScriptException {
        String strprice;
        double price = 0.0;
        if (fileMode) {
            while (true) {
                try {
                    strprice = userScanner.nextLine().trim();
                    price = Float.parseFloat(strprice);
                    if (price <= 0) throw new NotInDeclaredLimitsException();
                    break;
                } catch (NotInDeclaredLimitsException exception) {
                    showErrorAlert(messages.getString("alert.annual.turnover.zero"));
                    throw new WrongInputInScriptException();
                } catch (NumberFormatException exception) {
                    showErrorAlert(messages.getString("alert.annual.turnover.not.number"));
                    throw new WrongInputInScriptException();
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.annual.turnover.not.recognized"));
                    throw new WrongInputInScriptException();
                } catch (NullPointerException | IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                }
            }
        }
        else {
            try {
                strprice = annualTurnoverField.getText().trim();
                price = Float.parseFloat(strprice);
                if (price <= 0) throw new NotInDeclaredLimitsException();
            } catch (NotInDeclaredLimitsException exception) {
                showErrorAlert(messages.getString("alert.annual.turnover.zero"));
            } catch (NumberFormatException exception) {
                showErrorAlert(messages.getString("alert.annual.turnover.not.number"));
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.annual.turnover.not.recognized"));
                System.exit(0);
            } catch (NullPointerException | IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            }
        }
        return price;
    }

    public OrganizationType organizationTypeAsker() throws WrongInputInScriptException {
        OrganizationType organizationType = null;
        String strOrgType;
        if (fileMode) {
            while (true) {
                try {
                    strOrgType = userScanner.nextLine().trim();
                    organizationType = OrganizationType.valueOf(strOrgType.toUpperCase());
                    break;
                } catch (IllegalArgumentException exception) {
                    showErrorAlert(messages.getString("alert.type.not.exist"));
                    throw new WrongInputInScriptException();
                } catch (IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.type.not.recognized"));
                    throw new WrongInputInScriptException();
                }
            }
        }
        else {
            try {
                organizationType = orgTypeComboBox.getSelectionModel().getSelectedItem();
            } catch (IllegalArgumentException exception) {
                showErrorAlert(messages.getString("alert.type.not.exist"));
            } catch (IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.type.not.recognized"));
                System.exit(0);
            }
        }
        return organizationType;
    }

    public String streetAsker() throws WrongInputInScriptException {
        String street = "";
        if (fileMode) {
            while (true) {
                try {
                    street = userScanner.nextLine().trim();
                    if (street.equals("")) throw new NotDeclaredValueException();
                    break;
                } catch (NotDeclaredValueException exception) {
                    showErrorAlert(messages.getString("alert.street.empty"));
                    throw new WrongInputInScriptException();
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.street.not.recognized"));
                    throw new WrongInputInScriptException();
                } catch (IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                }
            }
        }
        else {
            try {
                street = streetField.getText().trim();
                if (street.equals("")) throw new NotDeclaredValueException();
            } catch (NotDeclaredValueException exception) {
                showErrorAlert(messages.getString("alert.street.empty"));
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.street.not.recognized"));
                System.exit(0);
            } catch (IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            }
        }
        return street;
    }

    public String zipCodeAsker() throws WrongInputInScriptException {
        String zipCode = "";
        if (fileMode) {
            while (true) {
                try {
                    zipCode = userScanner.nextLine().trim();
                    if (zipCode.equals("")) throw new NotDeclaredValueException();
                    break;
                } catch (NotDeclaredValueException exception) {
                    showErrorAlert(messages.getString("alert.zipcode.empty"));
                    throw new WrongInputInScriptException();
                } catch (NoSuchElementException exception) {
                    showErrorAlert(messages.getString("alert.zipcode.not.recognized"));
                    throw new WrongInputInScriptException();
                } catch (IllegalStateException exception) {
                    showErrorAlert(messages.getString("unexpected.error"));
                    System.exit(0);
                }
            }
        }
        else {
            try {
                zipCode = zipCodeField.getText().trim();
                if (zipCode.equals("")) throw new NotDeclaredValueException();
            } catch (NotDeclaredValueException exception) {
                showErrorAlert(messages.getString("alert.zipcode.empty"));
            } catch (NoSuchElementException exception) {
                showErrorAlert(messages.getString("alert.zipcode.not.recognized"));
                System.exit(0);
            } catch (IllegalStateException exception) {
                showErrorAlert(messages.getString("unexpected.error"));
                System.exit(0);
            }
        }
        return zipCode;
    }

    @Override
    public String toString() {
        return "ProductBuilder (helper class for user requests)";
    }

    public void setScene() {
        messages = ResourceBundle.getBundle("messages", this.locale);
        
        unitComboBox.getItems().addAll(UnitOfMeasure.values());
        orgTypeComboBox.getItems().addAll(OrganizationType.values());

        root.addRow(0, new Label(messages.getString("label.name")), nameField);
        root.addRow(1, new Label(messages.getString("label.x")), xField);
        root.addRow(2, new Label(messages.getString("label.y")), yField);
        root.addRow(3, new Label(messages.getString("label.price")), priceField);
        root.addRow(4, new Label(messages.getString("label.UOM")), unitComboBox);
        root.addRow(5, new Label(messages.getString("label.org.name")), orgNameField);
        root.addRow(6, new Label(messages.getString("label.org.AT")), annualTurnoverField);
        root.addRow(7, new Label(messages.getString("label.org.type")), orgTypeComboBox);
        root.addRow(8, new Label(messages.getString("label.org.st")), streetField);
        root.addRow(9, new Label(messages.getString("label.org.ZC")), zipCodeField);

        root.addRow(10, submitButton);

        this.stage.setScene(scene);
        this.stage.showAndWait();
    }
}
