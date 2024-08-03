package data;

public enum UnitOfMeasure {
    CENTIMETERS(0),
    PCS(1),
    LITERS(2),
    GRAMS(3);

    private final int value;

    UnitOfMeasure(int value) {
        this.value = value;
    }

    /**
     * Returns a comma-separated string of all units of measure type names.
     *
     * @return A string containing all units of measure type names, separated by commas.
     */
    public static String nameList() {
        String list = "";
        for (UnitOfMeasure unitOfMeasure : values()) {
            list += unitOfMeasure.name() + ", ";
        }
        return list.substring(0, list.length() - 2);
    }

    public static UnitOfMeasure valueOf(Integer value) {
        for (UnitOfMeasure unitOfMeasure : values()) {
            if (unitOfMeasure.value == value) {
                return unitOfMeasure;
            }
        }

        return null;
    }
}
