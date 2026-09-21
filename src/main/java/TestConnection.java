import db.DatabaseConnection;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection connection =
                    DatabaseConnection.getInstance().getConnection();

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connexion PostgreSQL réussie !");
            }

        } catch (Exception e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}