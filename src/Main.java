import DB.DatabaseConnection;
import presentation.ConsoleUI;
import presentation.ConsoleUIX;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Get the singleton instance of DatabaseConnection
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Display menu to choose between ConsoleUI and ConsoleUIX
                System.out.println("1. Run ConsoleUI");
                System.out.println("2. Run ConsoleUIX");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int option = getUserInput(scanner);

                switch (option) {
                    case 1:
                        // Run ConsoleUI
                        ConsoleUI consoleUI = new ConsoleUI();
                        consoleUI.run();
                        break;
                    case 2:
                        // Run ConsoleUIX
                        ConsoleUIX consoleUIX = new ConsoleUIX(connection);
                        consoleUIX.run();
                        break;
                    case 3:
                        // Exit the program
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
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
