package DAO;

import java.sql.SQLException;
import java.util.List;
import essentiel.doc.Document;

public interface DocumentDAO {
    void create(Document document) throws SQLException;

    Document read(String id) throws SQLException;

    void update(Document document) throws SQLException;

    void delete(String id) throws SQLException;

    List<Document> findAll() throws SQLException;

    /**
     * Mark a document as borrowed.
     * 
     * @param id the ID of the document to mark as borrowed
     * @throws SQLException if a database access error occurs
     */
    void emprunter(String id) throws SQLException;

    /**
     * Mark a document as returned.
     * 
     * @param id the ID of the document to mark as returned
     * @throws SQLException if a database access error occurs
     */
    void retourner(String id) throws SQLException;

    /**
     * Mark a document as reserved.
     * 
     * @param id the ID of the document to mark as reserved
     * @throws SQLException if a database access error occurs
     */
    void reserver(String id) throws SQLException;

    /**
     * Cancel the reservation of a document.
     * 
     * @param id the ID of the document to cancel the reservation
     * @throws SQLException if a database access error occurs
     */
    void annulerReservation(String id) throws SQLException;
}
