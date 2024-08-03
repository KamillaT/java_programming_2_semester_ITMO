package utilities;
import javafx.scene.paint.Color;

import java.util.Random;

public class ColorGenerator {
    public static Color generateColor(String creatorID) {
        // Use a random number generator to introduce additional randomness
        Random random = new Random(creatorID.hashCode());

        // Generate random values for red, green, and blue components
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Create and return the color
        return Color.rgb(red, green, blue);
    }
}
