package repository.jdbc;

import db.DatabaseConnection;
import model.Invoice;
import repository.InvoiceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InvoiceRepositoryJdbc implements InvoiceRepository {

    private final Connection connection;
    public InvoiceRepositoryJdbc() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Invoice> findAll() {
        return List.of();
    }

    @Override
    public void save(Invoice invoice) {

        String sql = """
        INSERT INTO invoices (
            id,
            invoice_number,
            payment_id,
            total_ht,
            vat_rate,
            vat_amount,
            total_ttc
        )
        VALUES (
            ?,
            'INV-' || EXTRACT(YEAR FROM CURRENT_DATE) || '-' ||
            LPAD(nextval('invoice_number_seq')::TEXT, 6, '0'),
            ?,
            ?,
            ?,
            ?,
            ?
        )
        RETURNING invoice_number
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, invoice.getId());
            ps.setObject(2, invoice.getPaymentId());
            ps.setBigDecimal(3, invoice.getTotalHT());
            ps.setBigDecimal(4, invoice.getVatRate());
            ps.setBigDecimal(5, invoice.getVatAmount());
            ps.setBigDecimal(6, invoice.getTotalTTC());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    invoice.setInvoiceNumber(
                            rs.getString("invoice_number")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la sauvegarde de la facture",
                    e
            );
        }
    }
}
