package repository.jdbc;

import db.DatabaseConnection;
import model.Reservation;
import repository.ReservationRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationRepositoryJdbc implements ReservationRepository {

    private Connection connection;

    public ReservationRepositoryJdbc() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = """
    
                INSERT INTO reservations (
        id,
        reservation_code,
        user_id,
        room_id,
        check_in,
        check_out,
        number_of_guests,
        number_of_nights,
        status
    )
    VALUES (
        ?,
        'RES-' || EXTRACT(YEAR FROM CURRENT_DATE) || '-' ||
        LPAD(nextval('reservation_code_seq')::TEXT, 6, '0'),
        ?,
        ?,
        ?,
        ?,
        ?,
        ?,
        ?
    )
    RETURNING reservation_code
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, reservation.getId());
            ps.setObject(2, reservation.getUserId());
            ps.setObject(3, reservation.getRoomId());
            ps.setDate(4, Date.valueOf(reservation.getCheckIn()));
            ps.setDate(5, Date.valueOf(reservation.getCheckOut()));
            ps.setInt(6, reservation.getNumberOfGuests());
            ps.setInt(7, reservation.getNumberOfNights());
            ps.setString(8, reservation.getStatus().name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reservation.setReservationCode(
                            rs.getString("reservation_code")
                    );
                }
                return  reservation;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la sauvegarde de la réservation",
                    e
            );
        }
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public boolean existsByCode(String reservationCode) {
        return false;
    }

    @Override
    public void update(Reservation reservation) {

    }

    @Override
    public void cancel(UUID id) {

    }

    @Override
    public void delete(UUID id) {

    }
}