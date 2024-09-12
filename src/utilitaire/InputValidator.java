package utilitaire;

import java.time.LocalDate;
import utilitaire.DateUtils;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    public String promptValidEmail(String message) {
        while (true) {
            String email = promptString(message);
            if (isValidEmail(email)) {
                return email;
            }
            System.out.println("Invalid email format. Please try again.");
        }
    }

    public String promptValidName(String message) {
        while (true) {
            String name = promptString(message);
            if (isValidName(name)) {
                return name;
            }
            System.out.println("Invalid name. Please enter a name containing only letters and spaces.");
        }
    }

    private boolean isValidName(String name) {
        // Check if name contains only letters and spaces
        return name != null && name.matches("[a-zA-Z\\s]+");
    }

    public int promptValidAge(String message) {
        while (true) {
            int age = promptInt(message);
            if (age > 0 && age < 120) {
                return age;
            }
            System.out.println("Invalid age. Please enter a valid age between 1 and 119.");
        }
    }

    public String promptValidCNE(String message) {
        while (true) {
            String cne = promptString(message);
            if (isValidCNE(cne)) {
                return cne;
            }
            System.out.println("Invalid CNE. Please try again.");
        }
    }

    public String promptValidCIN(String message) {
        while (true) {
            String cin = promptString(message);
            if (isValidCIN(cin)) {
                return cin;
            }
            System.out.println("Invalid CIN. Please try again.");
        }
    }

    private boolean isValidEmail(String email) {
        // Simple regex for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidCNE(String cne) {
        // Implement validation logic for CNE if applicable
        // This is a placeholder for demonstration purposes
        return cne != null && !cne.trim().isEmpty();
    }

    private boolean isValidCIN(String cin) {
        // Implement validation logic for CIN if applicable
        // This is a placeholder for demonstration purposes
        return cin != null && !cin.trim().isEmpty();
    }

    public String promptValidISBN(String message) {
        while (true) {
            String isbn = promptString(message);
            if (isValidISBN(isbn)) {
                return isbn;
            }
            System.out.println("Invalid ISBN. Please try again.");
        }
    }

    /*
     * Valid ISBN-10 Examples:
     * 0306406152
     * 00074625420
     * 0198526636
     * 0140177395
     * 0395067856
     */

    /*
     * Valid ISBN-13 Examples:
     * 9780306406157
     * 9781402894626
     * 9780198526636
     * 9780140177398
     * 9780395067850
     */

    private boolean isValidISBN(String isbn) {
        return isValidISBN10(isbn) || isValidISBN13(isbn);
    }

    private boolean isValidISBN10(String isbn) {
        if (isbn == null || isbn.length() != 10) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Integer.parseInt(String.valueOf(isbn.charAt(i)));
                sum += (digit * (10 - i));
            }
            String checkSum = isbn.substring(9);
            sum += "X".equals(checkSum) ? 10 : Integer.parseInt(checkSum);
            return sum % 11 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidISBN13(String isbn) {
        if (isbn == null || isbn.length() != 13) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(String.valueOf(isbn.charAt(i)));
                sum += (i % 2 == 0) ? digit : (digit * 3);
            }
            int checkDigit = Integer.parseInt(String.valueOf(isbn.charAt(12)));
            return (checkDigit == (10 - (sum % 10)) % 10);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
