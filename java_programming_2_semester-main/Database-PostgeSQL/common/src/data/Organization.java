package data;

import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Organization} class represents information about an organization.
 * It includes details such as ID, name, coordinates, creation date, annual turnover,
 * full name, employees count, type, and postal address.
 */
public class Organization implements Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double annualTurnover; //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле не может быть null

    /**
     * Constructs an organization with the specified details.
     *
     * @param id             the organization ID
     * @param name           the organization name
     * @param annualTurnover the organization annual turnover
     * @param type           the organization type
     * @param postalAddress  the organization postal address
     */
    public Organization(Long id, String name, Double annualTurnover, OrganizationType type, Address postalAddress) {
        this.id = id;
        this.name = name;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(Double annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString() {
        String info = "Organization № " + id + "\n";
        info += "Name: " + name + "\n";
        info += "Annual Turnover: " + annualTurnover + "\n";
        info += "Type: " + type + "\n";
        info += "Postal Address: \n" + postalAddress + "\n";

        return info;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id) + name.hashCode() + annualTurnover.hashCode()
                + type.hashCode() + postalAddress.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Organization otherOrganization = (Organization) obj;

        return Objects.equals(id, otherOrganization.id) &&
                Objects.equals(name, otherOrganization.name) &&
                Objects.equals(annualTurnover, otherOrganization.annualTurnover) &&
                Objects.equals(type, otherOrganization.type) &&
                Objects.equals(postalAddress, otherOrganization.postalAddress);
    }
}

