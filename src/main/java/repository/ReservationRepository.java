package repository;

import model.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findByCode(String reservationCode);

    List<Reservation> findAll();
    List<Reservation> findByUserId(UUID userId);
    boolean existsByCode(String reservationCode);

    void update(Reservation reservation);

    void cancel(UUID id);

    void delete(UUID id);
}