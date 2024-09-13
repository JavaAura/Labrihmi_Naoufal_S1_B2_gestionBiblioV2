package presentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import DB.DatabaseConnection;
import essentiel.Bibliotheque;
import utilitaire.InputValidator;

public class ConsoleUI {
    private Bibliotheque bibliotheque;
    private InputValidator inputValidator;
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in); // Initialize the Scanner object
        this.inputValidator = new InputValidator(scanner); // Pass the Scanner to InputValidator
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            bibliotheque = new Bibliotheque(connection);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            System.exit(1); // Exit if database connection fails
        }
    }

    public void run() {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Create Livre");
            System.out.println("2. Create Magazine");
            System.out.println("3. Create TheseUniversitaire");
            System.out.println("4. Create JournalScientifique");
            System.out.println("5. Read Document");
            System.out.println("6. Update Document");
            System.out.println("7. Delete Document");
            System.out.println("8. List All Documents");
            System.out.println("9. Borrow Document");
            System.out.println("10. Return Document");
            System.out.println("11. Reserve Document");
            System.out.println("12. Cancel Reservation");
            System.out.println("13. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        bibliotheque.createLivre();
                        break;
                    case 2:
                        bibliotheque.createMagazine();
                        break;
                    case 3:
                        bibliotheque.createTheseUniversitaire();
                        break;
                    case 4:
                        bibliotheque.createJournalScientifique();
                        break;
                    case 5:
                        bibliotheque.readDocument();
                        break;
                    case 6:
                        bibliotheque.updateDocument();
                        break;
                    case 7:
                        bibliotheque.deleteDocument();
                        break;
                    case 8:
                        bibliotheque.findAllDocuments();
                        break;
                    case 9:
                        bibliotheque.emprunterDocument();
                        break;
                    case 10:
                        bibliotheque.returneDocument();
                        break;
                    case 11:
                        bibliotheque.reserverDocument();
                        break;
                    case 12:
                        bibliotheque.annuleReserverDocument();
                        break;
                    case 13:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }
}
