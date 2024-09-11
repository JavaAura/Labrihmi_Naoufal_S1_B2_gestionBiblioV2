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

    private String promptString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private LocalDate promptValidDate(String message, boolean allowEmpty) {
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

    private int promptInt(String message) {
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

    private void createDocument(String type) throws SQLException {
        String id = generateNextId(type);
        String titre = promptString("Enter Title: ");
        String auteur = promptString("Enter Author: ");
        LocalDate datePublication = promptValidDate("Enter publication date (yyyy-MM-dd): ", false); // Ensure valid
                                                                                                     // date
        int nombreDePages = promptInt("Enter Number of Pages: ");
        boolean emprunter = false;
        boolean reservation = false;

        Document document;

        switch (type) {
            case "L":
                String isbn = promptString("Enter ISBN: ");
                document = new Livre(id, titre, auteur, datePublication, nombreDePages, emprunter, reservation, isbn);
                break;
            case "M":
                int numero = promptInt("Enter Issue Number: ");
                document = new Magazine(id, titre, auteur, datePublication, nombreDePages, emprunter, reservation,
                        numero);
                break;
            case "T":
                String university = promptString("Enter University: ");
                document = new TheseUniversitaire(id, titre, auteur, datePublication, nombreDePages, emprunter,
                        reservation, university);
                break;
            case "J":
                String domaineRecherche = promptString("Enter Research Domain: ");
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
        // Retrieve the next ID from the database or maintain a counter to generate new
        // IDs
        int nextNumber = getNextDocumentNumber(prefix);
        return prefix + "-" + nextNumber;
    }

    private int getNextDocumentNumber(String prefix) {
        // Implement logic to get the next number for the given prefix
        return 1; // Replace with actual logic
    }

    private void readDocument() throws SQLException {
        System.out.print("Enter Document ID to read: ");
        String id = scanner.nextLine();
        Document document = documentDAO.read(id);
        if (document != null) {
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
        } else {
            System.out.println("Document not found.");
        }
    }

    private void updateDocument() throws SQLException {
        System.out.print("Enter Document ID to update: ");
        String id = scanner.nextLine();
        Document document = documentDAO.read(id);
        if (document != null) {
            System.out.print("Enter new Title (leave empty to keep current): ");
            String titre = scanner.nextLine();
            if (!titre.isEmpty()) {
                document.setTitre(titre);
            }
            System.out.print("Enter new Author (leave empty to keep current): ");
            String auteur = scanner.nextLine();
            if (!auteur.isEmpty()) {
                document.setAuteur(auteur);
            }
            LocalDate newDatePublication = promptValidDate(
                    "Enter new Publication Date (yyyy-MM-dd, leave empty to keep current): ", true);
            if (newDatePublication != null) {
                document.setDatePublication(newDatePublication);
            }
            System.out.print("Enter new Number of Pages (leave empty to keep current): ");
            String nombreDePagesStr = scanner.nextLine();
            if (!nombreDePagesStr.isEmpty()) {
                int nombreDePages = Integer.parseInt(nombreDePagesStr);
                document.setNombreDePages(nombreDePages);
            }
            if (document instanceof Livre) {
                System.out.print("Enter new ISBN (leave empty to keep current): ");
                String isbn = scanner.nextLine();
                if (!isbn.isEmpty()) {
                    ((Livre) document).setIsbn(isbn);
                }
            } else if (document instanceof Magazine) {
                System.out.print("Enter new Issue Number (leave empty to keep current): ");
                String numeroStr = scanner.nextLine();
                if (!numeroStr.isEmpty()) {
                    int numero = Integer.parseInt(numeroStr);
                    ((Magazine) document).setNumero(numero);
                }
            } else if (document instanceof TheseUniversitaire) {
                System.out.print("Enter new University (leave empty to keep current): ");
                String university = scanner.nextLine();
                if (!university.isEmpty()) {
                    ((TheseUniversitaire) document).setUniversity(university);
                }
            } else if (document instanceof JournalScientifique) {
                System.out.print("Enter new Research Domain (leave empty to keep current): ");
                String domaineRecherche = scanner.nextLine();
                if (!domaineRecherche.isEmpty()) {
                    ((JournalScientifique) document).setDomaineRecherche(domaineRecherche);
                }
            }
            documentDAO.update(document);
            System.out.println("Document updated successfully.");
        } else {
            System.out.println("Document not found.");
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

    private void emprunterDocument() throws SQLException {
        System.out.print("Enter Document ID to borrow: ");
        String id = scanner.nextLine();

        Document document = documentDAO.read(id);

        if (document != null) {
            // Use the emprunter() method from Document class
            document.emprunter();
            documentDAO.emprunter(id); // Optionally update the database
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
