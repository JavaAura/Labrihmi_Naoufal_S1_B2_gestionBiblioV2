package DAO;

import java.sql.SQLException;
import java.util.List;

import essentiel.doc.Document;

public interface DocumentDAO {
    void create(Document document) throws SQLException;

    Document read(int id) throws SQLException;

    void update(Document document) throws SQLException;

    void delete(int id) throws SQLException;

    List<Document> findAll() throws SQLException;
}
