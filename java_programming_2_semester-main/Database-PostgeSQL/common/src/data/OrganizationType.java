package data;

import java.io.Serializable;

/**
 * The {@code OrganizationType} enum represents different types of organizations.
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL(0),
    GOVERNMENT(1),
    TRUST(2),
    OPEN_JOINT_STOCK_COMPANY(3);

    final int value;

    OrganizationType(int value) {
        this.value = value;
    }

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
        return nameList.substring(0, nameList.length() - 1);
    }

    public static OrganizationType valueOf(int value) {
        for (OrganizationType organizationType : values()) {
            if (organizationType.value == value) {
                return organizationType;
            }
        }

        return null;
    }
}
