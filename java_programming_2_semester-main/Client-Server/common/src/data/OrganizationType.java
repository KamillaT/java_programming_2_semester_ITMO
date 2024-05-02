package data;

import java.io.Serializable;

/**
 * The {@code OrganizationType} enum represents different types of organizations.
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    GOVERNMENT,
    TRUST,
    OPEN_JOINT_STOCK_COMPANY;

    /**
     * Returns a comma-separated string of all organization type names.
     *
     * @return A string containing all organization type names, separated by commas.
     */
    public static String nameList() {
        String nameList = "";
        for (OrganizationType organizationType : values()) {
            nameList += organizationType.name() + ",";
        }
        return nameList.substring(0,nameList.length() - 2);
    }
}
