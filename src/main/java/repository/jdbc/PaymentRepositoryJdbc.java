package repository.jdbc;


import db.DatabaseConnection;
import model.Payment;
import repository.PaymentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PaymentRepositoryJdbc implements PaymentRepository {

    private final Connection connection;
    public PaymentRepositoryJdbc() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


    @Override
    public List<Payment> findAll() {
        return List.of();
    }

    @Override
    public void save(Payment payment) {

        String sql = """
        INSERT INTO payments (
            id,
            reservation_id,
            amount,
            method,
            status
        )
        VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, payment.getId());
            ps.setObject(2, payment.getReservationId());
            ps.setBigDecimal(3, payment.getAmount());
            ps.setString(4, payment.getMethod().name());
            ps.setString(5, payment.getStatus().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la sauvegarde du paiement",
                    e
            );
        }
    }
}
