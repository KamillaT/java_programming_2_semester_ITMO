package utility.creator;

import data.*;
import exceptions.WrongInputInScriptException;

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
    public static Product productCreator(Scanner scanner) {
        try {
            ProductBuilder productBuilder = new ProductBuilder(scanner);
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
        }
        catch (WrongInputInScriptException exception) {
            System.err.println();
        }

        return new Product(IDGenerator.generateID(), name, new Coordinates(x, y), price, unitOfMeasure,
                new Organization(IDLongGenerator.generateID(), organizationName, annualTurnover, organizationType,
                        new Address(street, zipcode)));
    }
}
