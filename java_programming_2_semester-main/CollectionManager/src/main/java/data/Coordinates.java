package data;

/**
 * The {@code Coordinates} class represents a set of geographical coordinates.
 * It encapsulates the x and y coordinates and provides methods for retrieval,
 * equality comparison, and generating a hash code.
 */
public class Coordinates {
    private double x; //Поле не может быть null
    private double y;

    /**
     * Constructs a {@code Coordinates} object with the specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coordinates(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    @Override
    public String toString() {
        return "x = " + getX() + ", y = " + getY();
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x) + Double.hashCode(y);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Coordinates coord = (Coordinates) obj;
        return x == coord.x && y == coord.y;
    }
}