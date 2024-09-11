package DaoImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.DocumentDAO;
import essentiel.doc.Document;
import essentiel.doc.Livre;
import essentiel.doc.Magazine;
import essentiel.doc.TheseUniversitaire;
import essentiel.doc.JournalScientifique;

public class DocumentDAOImpl implements DocumentDAO {
    private Connection connection;

    public DocumentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Document document) throws SQLException {
        String sql;
        if (document instanceof Livre) {
            sql = "INSERT INTO livre (id, titre, auteur, date_publication, nombre_de_pages, isbn) VALUES (?, ?, ?, ?, ?, ?)";
        } else if (document instanceof Magazine) {
            sql = "INSERT INTO magazine (id, titre, auteur, date_publication, nombre_de_pages, numero) VALUES (?, ?, ?, ?, ?, ?)";
        } else if (document instanceof TheseUniversitaire) {
            sql = "INSERT INTO these_universitaire (id, titre, auteur, date_publication, nombre_de_pages, university) VALUES (?, ?, ?, ?, ?, ?)";
        } else if (document instanceof JournalScientifique) {
            sql = "INSERT INTO journal_scientifique (id, titre, auteur, date_publication, nombre_de_pages, domaine_recherche) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            throw new IllegalArgumentException("Unsupported document type");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getNextId(document));
            pstmt.setString(2, document.getTitre());
            pstmt.setString(3, document.getAuteur());
            pstmt.setDate(4, Date.valueOf(document.getDatePublication()));
            pstmt.setInt(5, document.getNombreDePages());

            if (document instanceof Livre) {
                pstmt.setString(6, ((Livre) document).getIsbn());
            } else if (document instanceof Magazine) {
                pstmt.setInt(6, ((Magazine) document).getNumero());
            } else if (document instanceof TheseUniversitaire) {
                pstmt.setString(6, ((TheseUniversitaire) document).getUniversity());
            } else if (document instanceof JournalScientifique) {
                pstmt.setString(6, ((JournalScientifique) document).getDomaineRecherche());
            }

            pstmt.executeUpdate();
        }
    }

    private String getNextId(Document document) throws SQLException {
        String prefix;
        if (document instanceof Livre) {
            prefix = "L-";
        } else if (document instanceof Magazine) {
            prefix = "M-";
        } else if (document instanceof TheseUniversitaire) {
            prefix = "T-";
        } else if (document instanceof JournalScientifique) {
            prefix = "J-";
        } else {
            throw new IllegalArgumentException("Unsupported document type");
        }

        String sql = "SELECT nextval('document_id_seq')"; // Use the sequence for generating unique IDs
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int id = rs.getInt(1);
                return prefix + id;
            } else {
                throw new SQLException("Failed to retrieve next ID");
            }
        }
    }

    @Override
    public Document read(String id) throws SQLException {
        Document document = null;
        String sql = "";
        if (id.startsWith("L-")) {
            sql = "SELECT * FROM livre WHERE id = ?";
        } else if (id.startsWith("M-")) {
            sql = "SELECT * FROM magazine WHERE id = ?";
        } else if (id.startsWith("T-")) {
            sql = "SELECT * FROM these_universitaire WHERE id = ?";
        } else if (id.startsWith("J-")) {
            sql = "SELECT * FROM journal_scientifique WHERE id = ?";
        } else {
            throw new IllegalArgumentException("Unsupported document type");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Common attributes
                    String titre = rs.getString("titre");
                    String auteur = rs.getString("auteur");
                    LocalDate datePublication = rs.getDate("date_publication").toLocalDate();
                    int nombreDePages = rs.getInt("nombre_de_pages");
                    boolean emprunter = rs.getBoolean("emprunter");
                    boolean reservation = rs.getBoolean("reservation");

                    if (id.startsWith("L-")) {
                        String isbn = rs.getString("isbn");
                        document = new Livre(id, titre, auteur, datePublication, nombreDePages, emprunter, reservation,
                                isbn);
                    } else if (id.startsWith("M-")) {
                        int numero = rs.getInt("numero");
                        document = new Magazine(id, titre, auteur, datePublication, nombreDePages, emprunter,
                                reservation, numero);
                    } else if (id.startsWith("T-")) {
                        String university = rs.getString("university");
                        document = new TheseUniversitaire(id, titre, auteur, datePublication, nombreDePages, emprunter,
                                reservation,
                                university);
                    } else if (id.startsWith("J-")) {
                        String domaineRecherche = rs.getString("domaine_recherche");
                        document = new JournalScientifique(id, titre, auteur, datePublication, nombreDePages, emprunter,
                                reservation,
                                domaineRecherche);
                    }
                }
            }
        }

        return document;
    }

    private String getTypeFromId(String id) {
        // Extract the type from the ID
        return id.split("-")[0]; // Assumes IDs are in format: "L-1", "M-1", etc.
    }

    private Livre readLivre(ResultSet rs) throws SQLException {
        return new Livre(
                rs.getString("id"),
                rs.getString("titre"),
                rs.getString("auteur"),
                rs.getDate("date_publication").toLocalDate(),
                rs.getInt("nombre_de_pages"),
                rs.getBoolean("emprunter"),
                rs.getBoolean("reservation"),
                rs.getString("isbn"));
    }

    private Magazine readMagazine(ResultSet rs) throws SQLException {
        return new Magazine(
                rs.getString("id"),
                rs.getString("titre"),
                rs.getString("auteur"),
                rs.getDate("date_publication").toLocalDate(),
                rs.getInt("nombre_de_pages"),
                rs.getBoolean("emprunter"),
                rs.getBoolean("reservation"),
                rs.getInt("numero"));
    }

    private TheseUniversitaire readTheseUniversitaire(ResultSet rs) throws SQLException {
        return new TheseUniversitaire(
                rs.getString("id"),
                rs.getString("titre"),
                rs.getString("auteur"),
                rs.getDate("date_publication").toLocalDate(),
                rs.getInt("nombre_de_pages"),
                rs.getBoolean("emprunter"),
                rs.getBoolean("reservation"),
                rs.getString("university"));
    }

    private JournalScientifique readJournalScientifique(ResultSet rs) throws SQLException {
        return new JournalScientifique(
                rs.getString("id"),
                rs.getString("titre"),
                rs.getString("auteur"),
                rs.getDate("date_publication").toLocalDate(),
                rs.getInt("nombre_de_pages"),
                rs.getBoolean("emprunter"),
                rs.getBoolean("reservation"),
                rs.getString("domaine_recherche"));
    }

    @Override
    public void update(Document document) throws SQLException {
        String sql;
        if (document instanceof Livre) {
            sql = "UPDATE livre SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, isbn = ? WHERE id = ?";
        } else if (document instanceof Magazine) {
            sql = "UPDATE magazine SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, numero = ? WHERE id = ?";
        } else if (document instanceof TheseUniversitaire) {
            sql = "UPDATE these_universitaire SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, university = ? WHERE id = ?";
        } else if (document instanceof JournalScientifique) {
            sql = "UPDATE journal_scientifique SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, domaine_recherche = ? WHERE id = ?";
        } else {
            throw new IllegalArgumentException("Unsupported document type");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, document.getTitre());
            pstmt.setString(2, document.getAuteur());
            pstmt.setDate(3, Date.valueOf(document.getDatePublication()));
            pstmt.setInt(4, document.getNombreDePages());

            if (document instanceof Livre) {
                pstmt.setString(5, ((Livre) document).getIsbn());
            } else if (document instanceof Magazine) {
                pstmt.setInt(5, ((Magazine) document).getNumero());
            } else if (document instanceof TheseUniversitaire) {
                pstmt.setString(5, ((TheseUniversitaire) document).getUniversity());
            } else if (document instanceof JournalScientifique) {
                pstmt.setString(5, ((JournalScientifique) document).getDomaineRecherche());
            }

            pstmt.setString(6, document.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql;
        String type = getTypeFromId(id); // You need to implement this method

        switch (type) {
            case "L":
                sql = "DELETE FROM livre WHERE id = ?";
                break;
            case "M":
                sql = "DELETE FROM magazine WHERE id = ?";
                break;
            case "T":
                sql = "DELETE FROM these_universitaire WHERE id = ?";
                break;
            case "J":
                sql = "DELETE FROM journal_scientifique WHERE id = ?";
                break;
            default:
                throw new IllegalArgumentException("Unsupported document type");
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Document> findAll() throws SQLException {
        List<Document> documents = new ArrayList<>();
        String[] tables = { "livre", "magazine", "these_universitaire", "journal_scientifique" };

        for (String table : tables) {
            String sql = "SELECT * FROM " + table;
            try (Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String type = getTypeFromId(id);

                    switch (type) {
                        case "L":
                            documents.add(readLivre(rs));
                            break;
                        case "M":
                            documents.add(readMagazine(rs));
                            break;
                        case "T":
                            documents.add(readTheseUniversitaire(rs));
                            break;
                        case "J":
                            documents.add(readJournalScientifique(rs));
                            break;
                        default:
                            throw new SQLException("Unknown document type");
                    }
                }
            }
        }
        return documents;
    }

    @Override
    public void emprunter(String id) throws SQLException {
        String sql = "UPDATE document SET emprunter = TRUE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void retourner(String id) throws SQLException {
        String sql = "UPDATE document SET emprunter = FALSE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void reserver(String id) throws SQLException {
        String sql = "UPDATE document SET reservation = TRUE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void annulerReservation(String id) throws SQLException {
        String sql = "UPDATE document SET reservation = FALSE WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

}
