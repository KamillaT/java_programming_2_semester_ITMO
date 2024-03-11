package utility;

/**
 * The {@code ConsolePrinter} class provides methods for printing output to the console with different formatting.
 */
public class ConsolePrinter {
    private static final String YELLOW = "\u001B[36m"; // ANSI escape code for yellow color
    private static final String RED = "\u001B[31m";    // ANSI escape code for red color
    private static final String WHITE = "\u001B[0m";   // ANSI escape code for default color

    /**
     * Prints the specified object to the console in yellow color.
     *
     * @param toOut the object to be printed
     */
    public static void printResult(Object toOut) {
        System.out.println(YELLOW + toOut + WHITE);
    }

    /**
     * Prints the specified object to the error stream.
     *
     * @param toError the object to be printed as an error
     */
    public static void printError(Object toError) {
        System.err.println(RED + toError + WHITE);
    }

    /**
     * Prints the specified object to the console without any formatting.
     *
     * @param toOut the object to be printed
     */
    public static void printInformation(Object toOut) {
        System.out.println(toOut);
    }
    public static void printInput(Object toIn) {System.out.print(toIn);}
}
