package repository.jdbc.mapper;

import model.Payment;
import model.enums.PaymentMethod;
import model.enums.PaymentStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PaymentMapper {

    public static Payment mapRowToPayment(ResultSet rs) throws SQLException {

        return new Payment(
                rs.getObject("id", UUID.class),
                rs.getObject("reservation_id", UUID.class),
                rs.getBigDecimal("amount"),
                PaymentMethod.valueOf(
                        rs.getString("method")
                ),
                PaymentStatus.valueOf(
                        rs.getString("status")
                )
        );
    }
}
