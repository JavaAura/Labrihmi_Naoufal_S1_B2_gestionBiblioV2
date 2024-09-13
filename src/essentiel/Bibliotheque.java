package essentiel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import DaoImpl.DocumentDAOImpl;
import essentiel.doc.Document;
import essentiel.doc.Livre;
import essentiel.doc.Magazine;
import essentiel.doc.TheseUniversitaire;
import essentiel.doc.JournalScientifique;
import utilitaire.InputValidator;

public class Bibliotheque {
    private DocumentDAOImpl documentDAO;
    private InputValidator inputValidator;
    private Scanner scanner = new Scanner(System.in); // Define at the class level

    public Bibliotheque(Connection connection) {
        this.documentDAO = new DocumentDAOImpl(connection);
        this.inputValidator = new InputValidator(new Scanner(System.in)); // Initialize input validator
    }

    public void createLivre() throws SQLException {
        createDocument("L");
    }

    public void createMagazine() throws SQLException {
        createDocument("M");
    }

    public void createTheseUniversitaire() throws SQLException {
        createDocument("T");
    }

    public void createJournalScientifique() throws SQLException {
        createDocument("J");
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

    private String generateNextId(String prefix) {
        int nextNumber = getNextDocumentNumber(prefix);
        return prefix + "-" + nextNumber;
    }

    private int getNextDocumentNumber(String prefix) {
        // Implement logic to get the next number for the given prefix
        return 1; // Replace with actual logic
    }

    public void readDocument() throws SQLException {
        String id = inputValidator.promptString("Enter Document ID to read: ");
        Document document = documentDAO.read(id);
        if (document != null) {
            displayDocumentDetails(document);
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

    public void updateDocument() throws SQLException {
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

    public void deleteDocument() throws SQLException {
        String id = inputValidator.promptString("Enter Document ID to delete: ");
        documentDAO.delete(id);
        System.out.println("Document deleted successfully.");
    }

    public void findAllDocuments() throws SQLException {
        List<Document> documents = documentDAO.findAll();
        for (Document doc : documents) {
            System.out.println(doc.toString());
        }
    }

    // emp with right (prof)
    public void emprunterDocument() throws SQLException {

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

    public void reserverDocument() throws SQLException {
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

    public void annuleReserverDocument() throws SQLException {
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

    public void returneDocument() throws SQLException {
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
