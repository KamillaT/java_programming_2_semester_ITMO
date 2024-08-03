package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The {@code Organization} class represents information about an organization.
 * It includes details such as ID, name, coordinates, creation date, annual turnover,
 * full name, employees count, type, and postal address.
 */
public class Product implements Comparable<Product>, Cloneable, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float price; //Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Organization manufacturer; //Поле не может быть null
    private Integer creatorId;

    /**
     * Constructs an organization with the specified details.
     *
     * @param id            the product ID
     * @param name          the product name
     * @param coordinates   the product coordinates
     * @param price         the product price
     * @param unitOfMeasure the product unit of measure
     * @param manufacturer  the product organization
     */

    public Product(Integer id, String name, Coordinates coordinates, LocalDate creationDate,
                   Float price, UnitOfMeasure unitOfMeasure, Organization manufacturer, Integer creatorId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
        this.creatorId = creatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Organization getOrganization() {
        return manufacturer;
    }

    public void setOrganization(Organization manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public int compareTo(Product product) {
        return ((Float) price).compareTo(product.getPrice());
    }

    @Override
    public String toString() {
        String info = "Product ID: " + id + "\n";
        info += "Name: " + name + "\n";
        info += "Coordinates: " + coordinates + "\n";
        info += "Creation date: " + creationDate + "\n";
        info += "Price: " + price + "\n";
        info += "Unit of measure: " + unitOfMeasure + "\n";
        info += "Organization: \n" + manufacturer + "\n";
        info += "Creator ID: " + creatorId + "\n";

        return info;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id) + name.hashCode() + coordinates.hashCode() + creationDate.hashCode()
                + Float.hashCode(price) + unitOfMeasure.hashCode() + manufacturer.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product otherProduct = (Product) obj;

        return Objects.equals(id, otherProduct.id) &&
                Objects.equals(name, otherProduct.name) &&
                Objects.equals(coordinates, otherProduct.coordinates) &&
                Objects.equals(creationDate, otherProduct.creationDate) &&
                Objects.equals(price, otherProduct.price) &&
                Objects.equals(unitOfMeasure, otherProduct.unitOfMeasure) &&
                Objects.equals(manufacturer, otherProduct.manufacturer);
    }

    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException exception) {
            return null;
        }
    }
}

