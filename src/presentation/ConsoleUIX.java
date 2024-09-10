package presentation;

import DaoImpl.UtilisateurDAOImpl;
import essentiel.Users.Utilisateur;
import essentiel.Users.Etudiant;
import essentiel.Users.Professeur;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUIX {
    private UtilisateurDAOImpl utilisateurDAO;
    private Scanner scanner;
    private Connection connection;

    public ConsoleUIX(Connection connection) {
        this.connection = connection;
        this.utilisateurDAO = new UtilisateurDAOImpl(connection);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            int option = getUserInput("Choose an option: ");

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
                    running = false;
                    System.out.println("Exiting...");
                    break;
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

    private int getUserInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    private void createUser() {
        System.out.println("Select user type:");
        System.out.println("1. Etudiant");
        System.out.println("2. Professeur");
        int userType = getUserInput("Enter choice: ");
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
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = getUserInput("Enter age: ");
        scanner.nextLine(); // Consume newline
        System.out.print("Enter CNE: ");
        String cne = scanner.nextLine();

        try {
            Etudiant etudiant = new Etudiant(null, name, email, age, cne); // ID will be generated
            utilisateurDAO.create(etudiant); // Use DAO method
            System.out.println("Etudiant created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Etudiant: " + e.getMessage());
        }
    }

    private void createProfesseur() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = getUserInput("Enter age: ");
        scanner.nextLine(); // Consume newline
        System.out.print("Enter CIN: ");
        String cin = scanner.nextLine();

        try {
            Professeur professeur = new Professeur(null, name, email, age, cin); // ID will be generated
            utilisateurDAO.create(professeur); // Use DAO method
            System.out.println("Professeur created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating Professeur: " + e.getMessage());
        }
    }

    private void readUser() {
        System.out.print("Enter user ID to read: ");
        scanner.nextLine(); // Clear the newline character from previous input
        String id = scanner.nextLine().trim(); // Read the ID input from the user

        if (id.isEmpty()) {
            System.out.println("Error: ID is empty. Please enter a valid ID.");
            return;
        }

        System.out.println("Executing query for ID: " + id);

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
        System.out.print("Enter user ID to update: ");
        scanner.nextLine(); // Clear the newline character from previous input
        String id = scanner.nextLine();

        if (id.isEmpty()) {
            System.out.println("Error: ID is empty. Please enter a valid ID.");
            return;
        }

        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter new Age: ");
        int age = getUserInput("Enter age: ");
        scanner.nextLine(); // Consume newline

        try {
            Utilisateur utilisateur = utilisateurDAO.read(id);
            if (utilisateur != null) {
                if (utilisateur instanceof Etudiant) {
                    System.out.print("Enter new CNE: ");
                    String cne = scanner.nextLine();
                    utilisateur = new Etudiant(id, name, email, age, cne);
                } else if (utilisateur instanceof Professeur) {
                    System.out.print("Enter new CIN: ");
                    String cin = scanner.nextLine();
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
        System.out.print("Enter user ID to delete: ");
        scanner.nextLine(); // Clear the newline character from previous input

        String id = scanner.nextLine();

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
