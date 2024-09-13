package utilitaire;

public class Logger {
    // ANSI color codes
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static void info(String message) {
        System.out.println(ANSI_GREEN + "[INFO] " + message + ANSI_RESET);
    }

    public static void error(String message) {
        System.out.println(ANSI_RED + "[ERROR] " + message + ANSI_RESET);
    }

    public static void warning(String message) {
        System.out.println(ANSI_YELLOW + "[WARNING] " + message + ANSI_RESET);
    }
}
