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
            System.out.println("1. Document Operations");
            System.out.println("2. Borrowing/Returning Operations");
            System.out.println("3. Exit");

            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (mainChoice) {
                    case 1:
                        handleDocumentOperations();
                        break;
                    case 2:
                        handleBorrowingReturningOperations();
                        break;
                    case 3:
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

    private void handleDocumentOperations() throws SQLException {
        System.out.println("Document Operations:");
        System.out.println("1. Create Document");
        System.out.println("2. Read Document");
        System.out.println("3. Update Document");
        System.out.println("4. Delete Document");
        System.out.println("5. List All Documents");

        int documentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (documentChoice) {
            case 1:
                handleCreateDocument();
                break;
            case 2:
                bibliotheque.readDocument();
                break;
            case 3:
                bibliotheque.updateDocument();
                break;
            case 4:
                bibliotheque.deleteDocument();
                break;
            case 5:
                bibliotheque.findAllDocuments();
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void handleCreateDocument() throws SQLException {
        System.out.println("Create Document:");
        System.out.println("1. Create Livre");
        System.out.println("2. Create Magazine");
        System.out.println("3. Create TheseUniversitaire");
        System.out.println("4. Create JournalScientifique");

        int createChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (createChoice) {
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
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void handleBorrowingReturningOperations() throws SQLException {
        System.out.println("Borrowing/Returning Operations:");
        System.out.println("1. Borrow Document");
        System.out.println("2. Return Document");
        System.out.println("3. Reserve Document");
        System.out.println("4. Cancel Reservation");

        int borrowReturnChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (borrowReturnChoice) {
            case 1:
                bibliotheque.emprunterDocument();
                break;
            case 2:
                bibliotheque.returneDocument();
                break;
            case 3:
                bibliotheque.reserverDocument();
                break;
            case 4:
                bibliotheque.annuleReserverDocument();
                break;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }
}
