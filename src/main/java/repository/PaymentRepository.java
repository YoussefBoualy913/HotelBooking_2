package repository;

import model.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    List<Payment> findAll();
    void save(Payment payment);
    Optional<Payment> findByReservationId(UUID reservationId);
}
