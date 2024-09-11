package utilitaire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Method to parse a date from a string
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            // System.out.println("Date invalide. Format attendu : yyyy-MM-dd");
            return null;
        }
    }

    // Method to format a LocalDate object into a string
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(FORMATTER) : null;
    }

    // Method to validate that a parsed date is within a reasonable range (e.g.,
    // between 1500 and the current year)
    public static boolean isDateValid(LocalDate date) {
        LocalDate earliestDate = LocalDate.of(1500, 1, 1);
        LocalDate latestDate = LocalDate.now();

        return date != null && (date.isEqual(earliestDate) || date.isAfter(earliestDate))
                && (date.isEqual(latestDate) || date.isBefore(latestDate));
    }
}
