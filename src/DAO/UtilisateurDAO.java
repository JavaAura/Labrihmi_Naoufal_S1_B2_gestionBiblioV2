package DAO;

import essentiel.Users.Utilisateur;
import java.sql.SQLException;
import java.util.List;

public interface UtilisateurDAO {
    void create(Utilisateur utilisateur) throws SQLException;

    Utilisateur read(String id) throws SQLException;

    void update(Utilisateur utilisateur) throws SQLException;

    void delete(String id) throws SQLException;

    List<Utilisateur> findAll() throws SQLException;
}
