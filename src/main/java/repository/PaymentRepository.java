package repository;

import model.Payment;

import java.util.List;

public interface PaymentRepository {
    List<Payment> findAll();
    void save(Payment payment);
}
