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
            System.out.println("9. Exit");

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
            // Gather new details
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

            System.out.print("Enter new Publication Date (yyyy-MM-dd, leave empty to keep current): ");
            String datePublicationStr = scanner.nextLine();
            if (!datePublicationStr.isEmpty()) {
                LocalDate datePublication = LocalDate.parse(datePublicationStr);
                document.setDatePublication(datePublication);
            }

            System.out.print("Enter new Number of Pages (leave empty to keep current): ");
            String nombreDePagesStr = scanner.nextLine();
            if (!nombreDePagesStr.isEmpty()) {
                int nombreDePages = Integer.parseInt(nombreDePagesStr);
                document.setNombreDePages(nombreDePages);
            }

            // Update specific attributes based on document type
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
        if (!documents.isEmpty()) {
            for (Document document : documents) {
                System.out.println("Document ID: " + document.getId());
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
                System.out.println();
            }
        } else {
            System.out.println("No documents found.");
        }
    }

    public static void main(String[] args) {
        new ConsoleUI().run();
    }
}
