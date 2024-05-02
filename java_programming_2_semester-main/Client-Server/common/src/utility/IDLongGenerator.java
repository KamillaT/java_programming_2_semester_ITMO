package utility;

import java.util.HashSet;
import java.util.Random;

/**
 * Utility class for generating unique IDs.
 */
public class IDLongGenerator {
    private static HashSet<Long> IDs = new HashSet<>();
    public static long generateID() {
        Long id = new Random().nextLong(Long.MAX_VALUE) + 1;
        if(IDs.contains(id)) {
            while(IDs.contains(id)){
                id = new Random().nextLong(Long.MAX_VALUE) + 1;
            }
        }
        IDs.add(id);
        return id;
    }
}
