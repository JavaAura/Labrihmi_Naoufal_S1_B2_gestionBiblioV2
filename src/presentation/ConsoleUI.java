package presentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import DB.DatabaseConnection;
import DaoImpl.DocumentDAOImpl;
import essentiel.doc.Document;
import essentiel.doc.Livre;
import essentiel.doc.Magazine;
import essentiel.doc.TheseUniversitaire;
import utilitaire.DateUtils;
import utilitaire.InputValidator;
import essentiel.doc.JournalScientifique;

public class ConsoleUI {
    private DocumentDAOImpl documentDAO;
    private InputValidator inputValidator;
    private Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in); // Initialize the Scanner object
        this.inputValidator = new InputValidator(scanner); // Pass the Scanner to InputValidator
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
            System.out.println("5. Read Document");
            System.out.println("6. Update Document");
            System.out.println("7. Delete Document");
            System.out.println("8. List All Documents");
            System.out.println("9. Borrow Document");
            System.out.println("10. Returne Document");
            System.out.println("11. Reserve Document");
            System.out.println("12. Annule Reservation");
            System.out.println("13. Exit");

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
                        readDocument();
                        break;
                    case 6:
                        updateDocument();
                        break;
                    case 7:
                        deleteDocument();
                        break;
                    case 8:
                        findAllDocuments();
                        break;
                    case 9:
                        emprunterDocument();
                        break;
                    case 10:
                        returneDocument();
                        break;
                    case 11:
                        reserverDocument();
                        break;
                    case 12:
                        annuleReserverDocument();
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

    private void createDocument(String type) throws SQLException {
        String id = generateNextId(type);
        String titre = inputValidator.promptString("Enter Title: ");
        String auteur = inputValidator.promptString("Enter Author: ");
        LocalDate datePublication = inputValidator.promptValidDate("Enter publication date (yyyy-MM-dd): ", false);
        int nombreDePages = inputValidator.promptInt("Enter Number of Pages: ");
        boolean emprunter = false;
        boolean reservation = false;

        Document document;

        switch (type) {
            case "L":
                String isbn = inputValidator.promptValidISBN("Enter ISBN: ");
                document = new Livre(id, titre, auteur, datePublication, nombreDePages, emprunter, reservation, isbn);
                break;
            case "M":
                int numero = inputValidator.promptInt("Enter Issue Number: ");
                document = new Magazine(id, titre, auteur, datePublication, nombreDePages, emprunter, reservation,
                        numero);
                break;
            case "T":
                String university = inputValidator.promptString("Enter University: ");
                document = new TheseUniversitaire(id, titre, auteur, datePublication, nombreDePages, emprunter,
                        reservation, university);
                break;
            case "J":
                String domaineRecherche = inputValidator.promptString("Enter Research Domain: ");
                document = new JournalScientifique(id, titre, auteur, datePublication, nombreDePages, emprunter,
                        reservation, domaineRecherche);
                break;
            default:
                throw new IllegalArgumentException("Invalid document type: " + type);
        }

        documentDAO.create(document);
        System.out.println(type + " created successfully.");
    }

    private void createLivre() throws SQLException {
        createDocument("L");
    }

    private void createMagazine() throws SQLException {
        createDocument("M");
    }

    private void createTheseUniversitaire() throws SQLException {
        createDocument("T");
    }

    private void createJournalScientifique() throws SQLException {
        createDocument("J");
    }

    private String generateNextId(String prefix) {
        int nextNumber = getNextDocumentNumber(prefix);
        return prefix + "-" + nextNumber;
    }

    private int getNextDocumentNumber(String prefix) {
        // Implement logic to get the next number for the given prefix
        return 1; // Replace with actual logic
    }

    private void readDocument() throws SQLException {
        String id = inputValidator.promptString("Enter Document ID to read: ");
        Document document = documentDAO.read(id);
        if (document != null) {
            displayDocumentDetails(document);
        } else {
            System.out.println("Document not found.");
        }
    }

    private void updateDocument() throws SQLException {
        String id = inputValidator.promptString("Enter Document ID to update: ");
        Document document = documentDAO.read(id);
        if (document != null) {
            updateDocumentFields(document);
            documentDAO.update(document);
            System.out.println("Document updated successfully.");
        } else {
            System.out.println("Document not found.");
        }
    }

    private void displayDocumentDetails(Document document) {
        System.out.println("Document Details:");
        System.out.println("ID: " + document.getId());
        System.out.println("Title: " + document.getTitre());
        System.out.println("Author: " + document.getAuteur());
        System.out.println("Publication Date: " + document.getDatePublication());
        System.out.println("Number of Pages: " + document.getNombreDePages());
        if (document instanceof Livre) {
            System.out.println("ISBN: " + ((Livre) document).getIsbn());
        } else if (document instanceof Magazine) {
            System.out.println("Issue Number: " + ((Magazine) document).getNumero());
        } else if (document instanceof TheseUniversitaire) {
            System.out.println("University: " + ((TheseUniversitaire) document).getUniversity());
        } else if (document instanceof JournalScientifique) {
            System.out.println("Research Domain: " + ((JournalScientifique) document).getDomaineRecherche());
        }
    }

    private void updateDocumentFields(Document document) {
        String titre = inputValidator.promptString("Enter new Title (leave empty to keep current): ");
        if (!titre.isEmpty()) {
            document.setTitre(titre);
        }

        String auteur = inputValidator.promptString("Enter new Author (leave empty to keep current): ");
        if (!auteur.isEmpty()) {
            document.setAuteur(auteur);
        }

        LocalDate newDatePublication = inputValidator.promptValidDate(
                "Enter new Publication Date (yyyy-MM-dd, leave empty to keep current): ", true);
        if (newDatePublication != null) {
            document.setDatePublication(newDatePublication);
        }

        String nombreDePagesStr = inputValidator
                .promptString("Enter new Number of Pages (leave empty to keep current): ");
        if (!nombreDePagesStr.isEmpty()) {
            int nombreDePages = Integer.parseInt(nombreDePagesStr);
            document.setNombreDePages(nombreDePages);
        }

        if (document instanceof Livre) {
            String isbn = inputValidator.promptString("Enter new ISBN (leave empty to keep current): ");
            if (!isbn.isEmpty()) {
                ((Livre) document).setIsbn(isbn);
            }
        } else if (document instanceof Magazine) {
            String numeroStr = inputValidator.promptString("Enter new Issue Number (leave empty to keep current): ");
            if (!numeroStr.isEmpty()) {
                int numero = Integer.parseInt(numeroStr);
                ((Magazine) document).setNumero(numero);
            }
        } else if (document instanceof TheseUniversitaire) {
            String university = inputValidator.promptString("Enter new University (leave empty to keep current): ");
            if (!university.isEmpty()) {
                ((TheseUniversitaire) document).setUniversity(university);
            }
        } else if (document instanceof JournalScientifique) {
            String domaineRecherche = inputValidator
                    .promptString("Enter new Research Domain (leave empty to keep current): ");
            if (!domaineRecherche.isEmpty()) {
                ((JournalScientifique) document).setDomaineRecherche(domaineRecherche);
            }
        }
    }

    private void deleteDocument() throws SQLException {
        System.out.print("Enter Document ID to delete: ");
        String id = scanner.nextLine();
        documentDAO.delete(id);
        System.out.println("Document deleted successfully.");
    }

    private void findAllDocuments() throws SQLException {
        List<Document> documents = documentDAO.findAll();
        for (Document doc : documents) {
            System.out.println(doc.toString()); // Explicitly calling toString() is optional
        }
    }

    // emp with right (prof)
    private void emprunterDocument() throws SQLException {
        System.out.print("Enter Document ID to borrow: ");
        String id = scanner.nextLine();

        // Read the document from the database
        Document document = documentDAO.read(id);

        if (document != null) {
            // Prompt for user type
            System.out.print("Enter user type (1 for Etudiant, 2 for Professeur): ");
            int userType = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Check the type of the document and the type of the user
            boolean canBorrow = true;
            if (document instanceof TheseUniversitaire && userType == 1) {
                System.out.println("Etudiants cannot borrow Thèses Universitaires.");
                canBorrow = false;
            }

            if (canBorrow) {
                // Use the emprunter() method from Document class
                document.emprunter();
                documentDAO.emprunter(id); // Optionally update the database
                System.out.println("Document borrowed successfully.");
            } else {
                System.out.println("Unable to borrow the document.");
            }
        } else {
            System.out.println("Document not found.");
        }
    }

    private void reserverDocument() throws SQLException {
        System.out.print("Enter Document ID to reserve: ");
        String id = scanner.nextLine();

        Document document = documentDAO.read(id);

        if (document != null) {
            document.reserver(); // Call reserver() method from Document class
            documentDAO.reserver(id); // Update the reservation status in the
                                      // database if needed
        } else {
            System.out.println("Document not found.");
        }
    }

    private void annuleReserverDocument() throws SQLException {
        System.out.print("Enter Document ID to cancel reservation: ");
        String id = scanner.nextLine();

        Document document = documentDAO.read(id);

        if (document != null) {
            document.annulerReservation(); // Call annulerReservation() method from Document class
            documentDAO.annulerReservation(id); // Update the reservation status in the database
        } else {
            System.out.println("Document not found.");
        }
    }

    private void returneDocument() throws SQLException {
        System.out.print("Enter Document ID to return: ");
        String id = scanner.nextLine();

        Document document = documentDAO.read(id);

        if (document != null) {
            document.retourner(); // Call retourner() method from Document class
            documentDAO.retourner(id); // Update the borrowing status in the database
        } else {
            System.out.println("Document not found.");
        }
    }

}
