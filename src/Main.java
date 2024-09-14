import DataBase.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import presentation.ConsoleUI;
import presentation.ConsoleUIX;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Obtenir l'instance Singleton de DatabaseConnection
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            connection = dbConnection.getConnection();

            while (true) {
                // Affichage du menu pour choisir entre ConsoleUI, ConsoleUIX, et ConsoleUIE
                System.out.println("\n--- Menu Principal ---");
                System.out.println("1. Run Console of Document");
                System.out.println("2. Run Console of Users");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int option = getUserInput(scanner);

                switch (option) {
                    case 1 -> {
                        // Exécuter ConsoleUI pour la gestion des documents
                        ConsoleUI consoleUI = new ConsoleUI();
                        consoleUI.run();
                    }
                    case 2 -> {
                        // Exécuter ConsoleUIX pour la gestion des utilisateurs
                        ConsoleUIX consoleUIX = new ConsoleUIX(connection);
                        consoleUIX.run();
                    }
                    case 3 -> {
                        // Quitter le programme
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } finally {
            // Fermer les ressources (connexion et scanner)
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.out.println("Error closing the database connection: " + e.getMessage());
                }
            }
            scanner.close();
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
