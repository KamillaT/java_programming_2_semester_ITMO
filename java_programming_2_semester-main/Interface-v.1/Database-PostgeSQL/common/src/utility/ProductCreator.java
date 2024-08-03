package utility;

import data.*;
import exceptions.WrongInputInScriptException;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class ProductCreator {
    private static String name;
    private static double x;
    private static double y;
    private static float price;
    private static UnitOfMeasure unitOfMeasure;
    private static String organizationName;
    private static double annualTurnover;
    private static OrganizationType organizationType;
    private static String street;
    private static String zipcode;

    public static Product productCreator(Stage stage, Scanner userScanner, boolean fileMode, Locale locale) {
        ProductBuilder productBuilder = new ProductBuilder(stage, userScanner, fileMode, locale);
        if (fileMode) {
            askProductBuilder(productBuilder);
        }
        else {
            productBuilder.submitButton.setOnAction(event -> {
                askProductBuilder(productBuilder);
            });
            productBuilder.setScene();
        }
        return new Product(0, name, new Coordinates(x, y), LocalDate.now(), price, unitOfMeasure,
                new Organization(0L, organizationName, annualTurnover, organizationType,
                        new Address(street, zipcode)), 0);
    }

    private static void askProductBuilder(ProductBuilder productBuilder) {
        try {
            name = productBuilder.nameAsker();
            x = productBuilder.xAsker();
            y = productBuilder.yAsker();
            price = productBuilder.priceAsker();
            unitOfMeasure = productBuilder.unitOfMeasureAsker();
            organizationName = productBuilder.organizationNameAsker();
            annualTurnover = productBuilder.annualTurnoverAsker();
            organizationType = productBuilder.organizationTypeAsker();
            street = productBuilder.streetAsker();
            zipcode = productBuilder.zipCodeAsker();
        } catch (WrongInputInScriptException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect input");
            alert.setHeaderText(null);
            alert.setContentText("Wrong input in script!");
            alert.showAndWait();
        }
    }
}
