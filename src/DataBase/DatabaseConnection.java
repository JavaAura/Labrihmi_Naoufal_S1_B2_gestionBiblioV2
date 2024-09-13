package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            String url = "jdbc:postgresql://localhost:5432/Biblio";
            String username = System.getenv("DB_USERNAME");
            String password = System.getenv("DB_PASSWORD");

            if (username == null || password == null) {
                throw new IllegalArgumentException("Database credentials not found in environment variables.");
            }

            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new SQLException("Failed to create the database connection.", ex);
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
