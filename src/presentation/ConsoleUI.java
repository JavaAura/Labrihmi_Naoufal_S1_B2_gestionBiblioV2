package presentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import DB.DatabaseConnection;
import DaoImpl.DocumentDAOImpl;
import essentiel.doc.Document;
import essentiel.doc.Livre;
import essentiel.doc.Magazine;
import essentiel.doc.TheseUniversitaire;
import essentiel.doc.JournalScientifique;

public class ConsoleUI {
    private DocumentDAOImpl documentDAO;
    private Scanner scanner;

    public ConsoleUI() {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            documentDAO = new DocumentDAOImpl(connection);
            scanner = new Scanner(System.in);
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
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        createLivre();
                        break;
                    case 2:
                        createMagazine();
                        break;
                    case 3:
                        createTheseUniversitaire();
                        break;
                    case 4:
                        createJournalScientifique();
                        break;
                    case 5:
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

    private void createLivre() throws SQLException {
        // Generate ID and collect other details
        String id = generateNextId("L");

        System.out.print("Enter Title: ");
        String titre = scanner.nextLine();

        System.out.print("Enter Author: ");
        String auteur = scanner.nextLine();

        System.out.print("Enter Publication Date (yyyy-MM-dd): ");
        LocalDate datePublication = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Number of Pages: ");
        int nombreDePages = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        Livre livre = new Livre(id, titre, auteur, datePublication, nombreDePages, isbn);
        documentDAO.create(livre);
        System.out.println("Livre created successfully.");
    }

    private void createMagazine() throws SQLException {
        // Generate ID and collect other details
        String id = generateNextId("M");

        System.out.print("Enter Title: ");
        String titre = scanner.nextLine();

        System.out.print("Enter Author: ");
        String auteur = scanner.nextLine();

        System.out.print("Enter Publication Date (yyyy-MM-dd): ");
        LocalDate datePublication = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Number of Pages: ");
        int nombreDePages = scanner.nextInt();

        System.out.print("Enter Issue Number: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Magazine magazine = new Magazine(id, titre, auteur, datePublication, nombreDePages, numero);
        documentDAO.create(magazine);
        System.out.println("Magazine created successfully.");
    }

    private void createTheseUniversitaire() throws SQLException {
        // Generate ID and collect other details
        String id = generateNextId("T");

        System.out.print("Enter Title: ");
        String titre = scanner.nextLine();

        System.out.print("Enter Author: ");
        String auteur = scanner.nextLine();

        System.out.print("Enter Publication Date (yyyy-MM-dd): ");
        LocalDate datePublication = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Number of Pages: ");
        int nombreDePages = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter University: ");
        String university = scanner.nextLine();

        TheseUniversitaire theseUniversitaire = new TheseUniversitaire(id, titre, auteur, datePublication,
                nombreDePages, university);
        documentDAO.create(theseUniversitaire);
        System.out.println("Thèse Universitaire created successfully.");
    }

    private void createJournalScientifique() throws SQLException {
        // Generate ID and collect other details
        String id = generateNextId("J");

        System.out.print("Enter Title: ");
        String titre = scanner.nextLine();

        System.out.print("Enter Author: ");
        String auteur = scanner.nextLine();

        System.out.print("Enter Publication Date (yyyy-MM-dd): ");
        LocalDate datePublication = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter Number of Pages: ");
        int nombreDePages = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Research Domain: ");
        String domaineRecherche = scanner.nextLine();

        JournalScientifique journalScientifique = new JournalScientifique(id, titre, auteur, datePublication,
                nombreDePages, domaineRecherche);
        documentDAO.create(journalScientifique);
        System.out.println("Journal Scientifique created successfully.");
    }

    private String generateNextId(String prefix) {
        // Retrieve the next ID from the database or maintain a counter to generate new
        // IDs
        // Here, we just simulate this by incrementing a static counter
        // Implement logic to get the current highest ID from the database and increment
        // it
        // For now, this is a placeholder to generate an ID

        // Example implementation (replace with actual logic):
        int nextNumber = getNextDocumentNumber(prefix);
        return prefix + "-" + nextNumber;
    }

    private int getNextDocumentNumber(String prefix) {
        // Implement logic to get the next number for the given prefix
        // For example, querying the database to find the highest number with the given
        // prefix and increment it
        // For now, this is a placeholder and should be replaced with actual database
        // logic

        // Example static increment (replace with actual implementation):
        return 1; // Replace with actual logic
    }

    public static void main(String[] args) {
        new ConsoleUI().run();
    }
}
