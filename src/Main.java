import DataBase.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import presentation.ConsoleUI;
import presentation.ConsoleUIX;

public class Main {
    public static void main(String[] args) {
        try {
            // Get the singleton instance of DatabaseConnection
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Display menu to choose between ConsoleUI, ConsoleUIX, and ConsoleUIE
                System.out.println("1. Run Console of Document");
                System.out.println("2. Run Console of Users");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int option = getUserInput(scanner);

                switch (option) {
                    case 1 -> {
                        // Run ConsoleUI
                        ConsoleUI consoleUI = new ConsoleUI();
                        consoleUI.run();
                    }
                    case 2 -> {
                        // Run ConsoleUIX
                        ConsoleUIX consoleUIX = new ConsoleUIX(connection);
                        consoleUIX.run();
                    }
                    case 3 -> {
                        // Exit the program
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    private static int getUserInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextInt();
    }
}
