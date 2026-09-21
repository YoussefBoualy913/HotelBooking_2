package config;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void createUsersTable() {

        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id UUID PRIMARY KEY,
                    fullName VARCHAR(100) NOT NULL,
                    email VARCHAR(150) NOT NULL UNIQUE,
                    phone VARCHAR(20),
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(20) NOT NULL DEFAULT 'CLIENT'
                )
                """;

        try (Connection connection =
                     DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);

            System.out.println("Table users créée avec succès.");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la création de la table users", e
            );
        }
    }
}