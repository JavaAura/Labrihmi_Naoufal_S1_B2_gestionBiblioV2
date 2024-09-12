package DaoImpl;

import DAO.UtilisateurDAO;
import essentiel.Users.Utilisateur;
import essentiel.Users.Etudiant;
import essentiel.Users.Professeur;
import essentiel.doc.Document;
import essentiel.doc.TheseUniversitaire;
import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private Connection connection;

    public UtilisateurDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private String generateNextId(String type) throws SQLException {
        String prefix = "";
        if ("Etudiant".equals(type)) {
            prefix = "E-";
        } else if ("Professeur".equals(type)) {
            prefix = "P-";
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        String sql = "SELECT COALESCE(MAX(SUBSTRING(id FROM '[0-9]+')::INT), 0) + 1 AS next_id FROM " +
                (type.equals("Etudiant") ? "etudiant" : "professeur");
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return prefix + rs.getInt("next_id");
            }
        }
        throw new SQLException("Failed to generate next ID.");
    }

    @Override
    public void create(Utilisateur utilisateur) throws SQLException {
        String type = utilisateur instanceof Etudiant ? "Etudiant" : "Professeur";
        String id = generateNextId(type);

        String sql = "INSERT INTO " + (type.equals("Etudiant") ? "etudiant" : "professeur") +
                " (id, name, email, age" +
                (type.equals("Etudiant") ? ", CNE" : ", CIN") + ") VALUES (?, ?, ?, ?" +
                (type.equals("Etudiant") ? ", ?" : ", ?") + ")";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, utilisateur.getName());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setInt(4, utilisateur.getAge());

            if (utilisateur instanceof Etudiant) {
                stmt.setString(5, ((Etudiant) utilisateur).getCne());
            } else if (utilisateur instanceof Professeur) {
                stmt.setString(5, ((Professeur) utilisateur).getCin());
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Utilisateur> read(String id) throws SQLException {
        Utilisateur utilisateur = null;

        // First check in the etudiant table
        String sqlEtudiant = "SELECT * FROM etudiant WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlEtudiant)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    utilisateur = new Etudiant(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getString("CNE"));
                }
            }
        }

        // If not found in etudiant table, check in the professeur table
        if (utilisateur == null) {
            String sqlProfesseur = "SELECT * FROM professeur WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sqlProfesseur)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        utilisateur = new Professeur(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getInt("age"),
                                rs.getString("CIN"));
                    }
                }
            }
        }

        return Optional.ofNullable(utilisateur);
    }

    @Override
    public void update(Utilisateur utilisateur) throws SQLException {
        String type = utilisateur instanceof Etudiant ? "Etudiant" : "Professeur";
        String sql = "UPDATE " + (type.equals("Etudiant") ? "etudiant" : "professeur") +
                " SET name = ?, email = ?, age = ?" +
                (type.equals("Etudiant") ? ", CNE = ?" : ", CIN = ?") +
                " WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, utilisateur.getName());
            stmt.setString(2, utilisateur.getEmail());
            stmt.setInt(3, utilisateur.getAge());

            if (utilisateur instanceof Etudiant) {
                stmt.setString(4, ((Etudiant) utilisateur).getCne());
            } else if (utilisateur instanceof Professeur) {
                stmt.setString(4, ((Professeur) utilisateur).getCin());
            }

            stmt.setString(5, utilisateur.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Utilisateur> findAll() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT u.id, u.name, u.email, u.age, " +
                "CASE WHEN e.CNE IS NOT NULL THEN 'Etudiant' " +
                "WHEN p.CIN IS NOT NULL THEN 'Professeur' END AS userType, " +
                "e.CNE, p.CIN " +
                "FROM utilisateur u " +
                "LEFT JOIN etudiant e ON u.id = e.id " +
                "LEFT JOIN professeur p ON u.id = p.id";

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                String userType = rs.getString("userType");

                Utilisateur utilisateur = null;

                if ("Etudiant".equals(userType)) { // It's an Etudiant
                    String cne = rs.getString("CNE");
                    utilisateur = new Etudiant(id, name, email, age, cne);
                } else if ("Professeur".equals(userType)) { // It's a Professeur
                    String cin = rs.getString("CIN");
                    utilisateur = new Professeur(id, name, email, age, cin);
                }

                if (utilisateur != null) { // Only add valid users
                    utilisateurs.add(utilisateur);
                }
            }
        }
        return utilisateurs;
    }

    public boolean canBorrow(Utilisateur utilisateur, Document document) {
        if (document instanceof TheseUniversitaire) {
            return utilisateur instanceof Professeur;
        }
        return true;
    }

}
