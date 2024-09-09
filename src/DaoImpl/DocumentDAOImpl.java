package DaoImpl;

import java.sql.*;
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
    public Document read(int id) throws SQLException {
        // Implement this method to retrieve a document by id
        return null;
    }

    @Override
    public void update(Document document) throws SQLException {
        // Implement this method to update a document
    }

    @Override
    public void delete(int id) throws SQLException {
        // Implement this method to delete a document by id
    }

    @Override
    public List<Document> findAll() throws SQLException {
        // Implement this method to retrieve all documents
        return new ArrayList<>();
    }
}
