import java.sql.Connection;
import java.sql.SQLException;

import DB.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try {
            // Get the database connection instance
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();

            // Get the connection
            Connection connection = dbConnection.getConnection();

            // Check if the connection is not null and print a success message
            // if (connection != null) {
            // System.out.println("Database connection successful!");
            // } else {
            // System.out.println("Failed to establish database connection.");
            // }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
