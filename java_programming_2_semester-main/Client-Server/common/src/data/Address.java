package data;

import java.util.Objects;
import java.io.Serializable;

/**
 * The {@code Address} class represents a street address.
 * It encapsulates the street information and provides methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Address implements Serializable {
    /**
     * The street and zipcode information of the address.
     */
    private String street; //Поле не может быть null
    private String zipCode; //Длина строки не должна быть больше 21, Поле не может быть null

    /**
     * Constructs an {@code Address} object with the specified street.
     *
     * @param street The street information of the address.
     * @param zipCode The zipcode information of the address.
     */
    public Address(String street, String zipCode) {

        this.street = street;
        this.zipCode = zipCode;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet() {this.street = street;}
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode() {this.zipCode = zipCode;}

    @Override
    public String toString() {
        String info = "Street: " + street + "\n";
        info += "ZipCode: " + zipCode + "\n";

        return info;
    }

    @Override
    public int hashCode() {
        return street.hashCode() + zipCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Address otherAddress = (Address) obj;

        return Objects.equals(street, otherAddress.street) &&
                Objects.equals(zipCode, otherAddress.zipCode);
    }
}
