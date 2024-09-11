package utilitaire;

import java.time.LocalDate;
import java.util.Scanner;

public class InputValidator {

    private Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public LocalDate promptValidDate(String message, boolean allowEmpty) {
        while (true) {
            String dateStr = promptString(message);
            if (allowEmpty && dateStr.isEmpty()) {
                return null; // No date provided
            }
            LocalDate datePublication = DateUtils.parseDate(dateStr);
            if (datePublication != null && DateUtils.isDateValid(datePublication)) {
                return datePublication;
            }
            System.out.println("Invalid date. Please try again.");
        }
    }

    public int promptInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next();
            System.out.print(message);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return value;
    }

    public boolean promptYesNo(String message) {
        while (true) {
            String response = promptString(message + " (y/n): ").toLowerCase();
            if (response.equals("y")) {
                return true;
            } else if (response.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}
