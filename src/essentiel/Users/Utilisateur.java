package essentiel.Users;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import DaoImpl.UtilisateurDAOImpl;

public abstract class Utilisateur {
    private static final Logger logger = Logger.getLogger(Utilisateur.class.getName());

    protected String id;
    protected String name;
    protected String email;
    protected int age;

    public Utilisateur(String id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return formatUserDetails();
    }

    // Abstract method for user type
    public abstract String getUserType();

    // Utility methods

    private String formatUserDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID: %s, Name: %s, Email: %s, Age: %d", id, name, email, age));

        if (this instanceof Etudiant) {
            sb.append(", CNE: ").append(((Etudiant) this).getCne());
        } else if (this instanceof Professeur) {
            sb.append(", CIN: ").append(((Professeur) this).getCin());
        }

        return sb.toString();
    }
}
