package utility;

import data.*;
import exceptions.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Utility class for getting all information about the organization from the user
 */
public class ProductBuilder {

    private Scanner userScanner;
    private boolean fileMode;

    public ProductBuilder(Scanner scanner) {
        this.userScanner = scanner;
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
    public String nameAsker() throws WrongInputInScriptException {
        String name;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter product's name: ");
                System.out.print(">");
                name = userScanner.nextLine().trim();
                if(fileMode) ConsolePrinter.printInformation(name);
                if (name.equals("")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("The 'name' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("The name isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    public Double xAsker() throws WrongInputInScriptException {
        String strX;
        double x;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter x coordinate: ");
                System.out.print(">");
                strX = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strX);
                x = Double.parseDouble(strX);
                if(strX.isEmpty()) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                System.out.println("Coordinate x cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                System.out.println("Coordinate x must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Coordinate x isn't recodnized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            }
        }
        return x;
    }

    public Double yAsker() throws WrongInputInScriptException {
        String strY;
        double y;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter y coordinate: ");
                System.out.print(">");
                strY = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strY);
                y = Double.parseDouble(strY);
                break;
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("Coordinate y must be a number");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Coordinate y isn't  recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return y;
    }
    public Float priceAsker() throws WrongInputInScriptException {
        String strprice;
        float price;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter price of the product: ");
                System.out.print(">");
                strprice = userScanner.nextLine().trim();
                if (fileMode) throw new WrongInputInScriptException();
                price = Float.parseFloat(strprice);
                if (price <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                ConsolePrinter.printError("The price of the product must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("The price of the product must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Annual turnover isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return price;
    }

    public UnitOfMeasure unitOfMeasureAsker() throws WrongInputInScriptException {
        String strUnitOfMeasure;
        UnitOfMeasure unitOfMeasure;
        while (true) {
            try {
                ConsolePrinter.printInformation("Units Of Measure List: " + UnitOfMeasure.nameList());
                ConsolePrinter.printInformation("Enter the unit of measure: ");
                System.out.print(">");
                strUnitOfMeasure = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strUnitOfMeasure);
                unitOfMeasure = UnitOfMeasure.valueOf(strUnitOfMeasure.toUpperCase());
                break;
            } catch (IllegalArgumentException exception) {
                ConsolePrinter.printError("This type does not exist! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Type isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            }
        }
        return unitOfMeasure;
    }

    public String organizationNameAsker() throws WrongInputInScriptException {
        String name;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter organization's name: ");
                System.out.print(">");
                name = userScanner.nextLine().trim();
                if(fileMode) ConsolePrinter.printInformation(name);
                if (name.equals("")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("The 'name' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("The name isn't recognized!");
                System.exit(0);
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return name;
    }

    public Double annualTurnoverAsker() throws WrongInputInScriptException {
        String strprice;
        double price;
        while (true) {
            try {
                ConsolePrinter.printInformation("Enter annual turnover: ");
                System.out.print(">");
                strprice = userScanner.nextLine().trim();
                if (fileMode) throw new WrongInputInScriptException();
                price = Float.parseFloat(strprice);
                if (price <= 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException exception) {
                ConsolePrinter.printError("The annual turnover must be greater than 0! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NumberFormatException exception) {
                ConsolePrinter.printError("The annual turnover must be a number! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Annual turnover isn't recognized!");
                System.exit(0);
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return price;
    }

    public OrganizationType organizationTypeAsker() throws WrongInputInScriptException {
        String strOrgType;
        OrganizationType organizationType;
        while (true) {
            try {
                ConsolePrinter.printInformation("Organization Type List: " + OrganizationType.nameList());
                ConsolePrinter.printInformation("Enter the organization type: ");
                System.out.print(">");
                strOrgType = userScanner.nextLine().trim();
                if (fileMode) ConsolePrinter.printInformation(strOrgType);
                organizationType = OrganizationType.valueOf(strOrgType.toUpperCase());
                break;
            } catch (IllegalArgumentException exception) {
                ConsolePrinter.printError("This type does not exist! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("Type isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            }
        }
        return organizationType;
    }

    public String streetAsker() throws WrongInputInScriptException {
        String street;
        while (true) {
            try {
                System.out.println("Enter organization's street: ");
                System.out.print(">");
                street = userScanner.nextLine().trim();
                if(fileMode) ConsolePrinter.printInformation(street);
                if (street.equals("")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("The 'street' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("The street isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return street;
    }

    public String zipCodeAsker() throws WrongInputInScriptException {
        String zipCode;
        while (true) {
            try {
                System.out.println("Enter organization's zipCode: ");
                System.out.print(">");
                zipCode = userScanner.nextLine().trim();
                if(fileMode) ConsolePrinter.printInformation(zipCode);
                if (zipCode.equals("")) throw new NotDeclaredValueException();
                break;
            } catch (NotDeclaredValueException exception) {
                ConsolePrinter.printError("The 'zipCode' value cannot be empty! Try again!");
                if (fileMode) throw new WrongInputInScriptException();
            } catch (NoSuchElementException exception) {
                ConsolePrinter.printError("The zipCode isn't recognized!");
                if (fileMode) throw new WrongInputInScriptException();
                System.exit(0);
            } catch (IllegalStateException exception) {
                ConsolePrinter.printError("Unexpected error!");
                System.exit(0);
            }
        }
        return zipCode;
    }

    @Override
    public String toString() {
        return "ProductBuilder (helper class for user requests)";
    }
}
