
package DAO;

import java.sql.SQLException;

public interface EmpruntDAO {
    boolean emprunter(String documentId, String userId) throws SQLException;

    String getDocumentType(String id) throws SQLException;

    String getUserType(String id) throws SQLException;

    boolean isValidDocumentType(String documentType, String documentId) throws SQLException;

    boolean isValidUserType(String userType, String userId) throws SQLException;
}
