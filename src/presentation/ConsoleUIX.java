package presentation;

import DaoImpl.UtilisateurDAOImpl;
import essentiel.Users.Etudiant;
import essentiel.Users.Professeur;
import essentiel.Users.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import utilitaire.InputValidator;

public class ConsoleUIX {
    private final UtilisateurDAOImpl utilisateurDAO;
    private final Scanner scanner;
    private Connection connection;
    private final InputValidator inputValidator;

    // ANSI color codes for console output
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    // Map for user creation logic to reduce redundancy
    private final Map<Integer, Runnable> userCreationActions;

    public ConsoleUIX(Connection connection) {
        this.connection = connection;
        this.utilisateurDAO = new UtilisateurDAOImpl(connection);
        this.scanner = new Scanner(System.in);
        this.inputValidator = new InputValidator(scanner);

        // Initialize user creation actions map
        this.userCreationActions = Map.of(
                1, this::createEtudiant,
                2, this::createProfesseur);
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            int option = inputValidator.promptInt("Choose an option: ");
            switch (option) {
                case 1 -> createUser();
                case 2 -> readUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> listUsers();
                case 6 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println(ANSI_RED + "Invalid option. Please try again." + ANSI_RESET);
            }
        }

        // scanner.close(); // Close scanner resource
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
        int userType = inputValidator.promptInt("Select user type: \n1. Etudiant\n2. Professeur\nEnter choice: ");
        Optional.ofNullable(userCreationActions.get(userType)).ifPresentOrElse(
                Runnable::run,
                () -> System.out.println(ANSI_RED + "Invalid user type. Please try again." + ANSI_RESET));
    }

    private void createEtudiant() {
        createUser((name, email, age) -> {
            String cne = inputValidator.promptValidCNE("Enter CNE: ");
            return new Etudiant(null, name, email, age, cne);
        });
    }

    private void createProfesseur() {
        createUser((name, email, age) -> {
            String cin = inputValidator.promptValidCIN("Enter CIN: ");
            return new Professeur(null, name, email, age, cin);
        });
    }

    private void createUser(TriFunction<String, String, Integer, Utilisateur> userCreator) {
        String name = inputValidator.promptValidName("Enter name: ");
        String email = inputValidator.promptValidEmail("Enter email: ");
        int age = inputValidator.promptValidAge("Enter age: ");

        try {
            Utilisateur utilisateur = userCreator.apply(name, email, age);
            utilisateurDAO.create(utilisateur);
            System.out.println(
                    ANSI_GREEN + utilisateur.getClass().getSimpleName() + " created successfully." + ANSI_RESET);
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error creating user: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void readUser() {
        String id = inputValidator.promptString("Enter user ID to read: ").trim();

        if (id.isEmpty()) {
            System.out.println(ANSI_RED + "Error: ID is empty. Please enter a valid ID." + ANSI_RESET);
            return;
        }

        try {
            utilisateurDAO.read(id)
                    .ifPresentOrElse(
                            utilisateur -> System.out.println(ANSI_GREEN + "User found: " + utilisateur + ANSI_RESET),
                            () -> System.out.println(ANSI_RED + "User not found." + ANSI_RESET));
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error reading user: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void updateUser() {
        String id = inputValidator.promptString("Enter user ID to update: ").trim();

        if (id.isEmpty()) {
            System.out.println(ANSI_RED + "Error: ID is empty. Please enter a valid ID." + ANSI_RESET);
            return;
        }

        try {
            utilisateurDAO.read(id).ifPresentOrElse(user -> {
                String name = inputValidator.promptValidName("Enter new Name: ");
                String email = inputValidator.promptValidEmail("Enter new Email: ");
                int age = inputValidator.promptValidAge("Enter new Age: ");

                Utilisateur updatedUser = (user instanceof Etudiant)
                        ? new Etudiant(id, name, email, age, inputValidator.promptValidCNE("Enter new CNE: "))
                        : new Professeur(id, name, email, age, inputValidator.promptValidCIN("Enter new CIN: "));

                try {
                    utilisateurDAO.update(updatedUser);
                    System.out.println(ANSI_GREEN + "User updated successfully." + ANSI_RESET);
                } catch (SQLException e) {
                    System.out.println(ANSI_RED + "Error updating user: " + e.getMessage() + ANSI_RESET);
                }
            }, () -> System.out.println(ANSI_RED + "User not found." + ANSI_RESET));
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error fetching user for update: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void deleteUser() {
        String id = inputValidator.promptString("Enter user ID to delete: ").trim();

        if (id.isEmpty()) {
            System.out.println(ANSI_RED + "Error: ID is empty. Please enter a valid ID." + ANSI_RESET);
            return;
        }

        try {
            utilisateurDAO.delete(id);
            System.out.println(ANSI_GREEN + "User deleted successfully." + ANSI_RESET);
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error deleting user: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void listUsers() {
        try {
            //Collection
            List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
            // process this list using a stream to print all users
            utilisateurs.stream()
                    .map(Utilisateur::toString)
                    .forEach(user -> System.out.println(ANSI_GREEN + user + ANSI_RESET)); // Use stream to print all
                                                                                          // users
        } catch (SQLException e) {
            System.out.println(ANSI_RED + "Error listing users: " + e.getMessage() + ANSI_RESET);
        }
    }

    // TriFunction interface to handle creating users (Etudiant and Professeur)
    @FunctionalInterface
    interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
}