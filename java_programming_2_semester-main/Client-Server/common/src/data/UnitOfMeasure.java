package data;

public enum UnitOfMeasure {
    CENTIMETERS,
    PCS,
    LITERS,
    GRAMS;

    /**
     * Returns a comma-separated string of all units of measure type names.
     *
     * @return A string containing all units of measure type names, separated by commas.
     */
    public static String nameList() {
        String list = "";
        for (UnitOfMeasure unitOfMeasure : values()){
            list += unitOfMeasure.name() + ", ";
        }
        return list.substring(0, list.length() - 2);
    }
}
