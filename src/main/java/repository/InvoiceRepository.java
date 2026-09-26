package repository;

import model.Invoice;
import model.Payment;

import java.util.List;

public interface InvoiceRepository {
    List<Invoice> findAll();
    void save(Invoice invoice);
}
