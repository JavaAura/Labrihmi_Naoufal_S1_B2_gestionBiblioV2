package presentation;

import DaoImpl.UtilisateurDAOImpl;
import essentiel.Users.Utilisateur;
import essentiel.Users.Etudiant;
import essentiel.Users.Professeur;
import utilitaire.InputValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUIX {
    private UtilisateurDAOImpl utilisateurDAO;
    private Scanner scanner;
    private Connection connection;
    private InputValidator inputValidator;

    public ConsoleUIX(Connection connection) {
        this.connection = connection;
        this.utilisateurDAO = new UtilisateurDAOImpl(connection);
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator(scanner); // Initialize InputValidator
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            int option = inputValidator.promptInt("Choose an option: ");

            switch (option) {
                case 1:
                    createUser();
                    break;
                case 2:
                    readUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    listUsers();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close(); // Close scanner resource
    }

    private void displayMenu() {
        System.out.println("1. Create User");
        System.out.println("2. Read User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("5. List Users");
        System.out.println("6. Exit");
    }

    private void createUser() {
        System.out.println("Select user type:");
        System.out.println("1. Etudiant");
        System.out.println("2. Professeur");
        int userType = inputValidator.promptInt("Enter choice: ");
        scanner.nextLine(); // Consume newline

        switch (userType) {
            case 1:
                createEtudiant();
                break;
            case 2:
                createProfesseur();
                break;
            default:
                System.out.println("Invalid user type. Please try again.");
        }
    }

    private void createEtudiant() {
        String name = inputValidator.promptString("Enter name: ");
        String email = inputValidator.promptValidEmail("Enter email: ");
        int age = inputValidator.promptValidAge("Enter age: ");
        String cne = inputValidator.promptValidCNE("Enter CNE: ");

        try {
            Etudiant etudiant = new Etudiant(null, name, email, age, cne); // ID will be generated
            utilisateurDAO.create(etudiant); // Use DAO method
            System.out.println("Etudiant created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Etudiant: " + e.getMessage());
        }
    }

    private void createProfesseur() {
        String name = inputValidator.promptString("Enter name: ");
        String email = inputValidator.promptValidEmail("Enter email: ");
        int age = inputValidator.promptValidAge("Enter age: ");
        String cin = inputValidator.promptValidCIN("Enter CIN: ");

        try {
            Professeur professeur = new Professeur(null, name, email, age, cin); // ID will be generated
            utilisateurDAO.create(professeur); // Use DAO method
            System.out.println("Professeur created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Professeur: " + e.getMessage());
        }
    }

    private void readUser() {
        String id = inputValidator.promptString("Enter user ID to read: ").trim();

        if (id.isEmpty()) {
            System.out.println("Error: ID is empty. Please enter a valid ID.");
            return;
        }

        System.out.println("User for ID " + id + " is:");

        try {
            Utilisateur utilisateur = utilisateurDAO.read(id);
            if (utilisateur != null) {
                System.out.println(utilisateur);
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error reading user: " + e.getMessage());
        }
    }

    private void updateUser() {
        String id = inputValidator.promptString("Enter user ID to update: ").trim();

        if (id.isEmpty()) {
            System.out.println("Error: ID is empty. Please enter a valid ID.");
            return;
        }

        String name = inputValidator.promptString("Enter new Name: ");
        String email = inputValidator.promptValidEmail("Enter new Email: ");
        int age = inputValidator.promptValidAge("Enter new Age: ");

        try {
            Utilisateur utilisateur = utilisateurDAO.read(id);
            if (utilisateur != null) {
                if (utilisateur instanceof Etudiant) {
                    String cne = inputValidator.promptValidCNE("Enter new CNE: ");
                    utilisateur = new Etudiant(id, name, email, age, cne);
                } else if (utilisateur instanceof Professeur) {
                    String cin = inputValidator.promptValidCIN("Enter new CIN: ");
                    utilisateur = new Professeur(id, name, email, age, cin);
                }

                utilisateurDAO.update(utilisateur);
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    private void deleteUser() {
        String id = inputValidator.promptString("Enter user ID to delete: ").trim();

        if (id.isEmpty()) {
            System.out.println("Error: ID is empty. Please enter a valid ID.");
            return;
        }

        try {
            utilisateurDAO.delete(id);
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    private void listUsers() {
        try {
            List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
            for (Utilisateur utilisateur : utilisateurs) {
                System.out.println(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println("Error listing users: " + e.getMessage());
        }
    }
}
