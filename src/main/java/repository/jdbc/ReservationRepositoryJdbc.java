package repository.jdbc;

import db.DatabaseConnection;
import exception.ReservationNotFoundException;
import model.Reservation;
import model.enums.ReservationStatus;
import repository.ReservationRepository;
import repository.jdbc.mapper.ResrvationMapper;

import java.sql.*;
import java.util.ArrayList;
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
    public Optional<Reservation> findByCode(String reservationCode) {
        String sql = """
            SELECT *
            FROM reservations
            WHERE reservation_code = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, reservationCode);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(ResrvationMapper.mapRowToReservation(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error finding reservation by code",
                    e
            );
        }

        return Optional.empty();
    }

    @Override
    public boolean existsByCode(String reservationCode) {
        return false;
    }

    @Override
    public void update(Reservation reservation) {

    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
      String sql= """
               select * from reservations 
               where user_id = ? and status <> ?
               """;
       try (PreparedStatement statement = connection.prepareStatement(sql)){

           statement.setObject(1,userId);
           statement.setObject(2,ReservationStatus.CANCELLED.name());
           try (ResultSet rs = statement.executeQuery()){
               List<Reservation> reservations = new ArrayList<>();
               while (rs.next()) {
                   reservations.add(ResrvationMapper.mapRowToReservation(rs));
               }
               return reservations;
           }
       } catch (SQLException e) {
           throw new RuntimeException("errur lore de chargement des reservations"+e);
       }
    }
    public List<Reservation> findAll() {
      String  sql = """
                select * from reservations
                where status <> ?
                """;
        try (PreparedStatement statement= connection.prepareStatement(sql)){

           statement.setObject(1, ReservationStatus.CANCELLED.name());
           try (ResultSet rs = statement.executeQuery()){
               List<Reservation> reservations = new ArrayList<>();
               while (rs.next()) {
                   reservations.add(ResrvationMapper.mapRowToReservation(rs));
               }
               return reservations;
           }

        }catch (SQLException e){
            throw new RuntimeException("errur lore de chargement des reservations"+e);
        }
    }
    @Override
    public void cancel(UUID id) {
        String sql = """
            UPDATE reservations
            SET status = ?
            WHERE id = ?
              AND status <> ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ReservationStatus.CANCELLED.name());
            statement.setObject(2, id);
            statement.setString(3, ReservationStatus.CANCELLED.name());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new ReservationNotFoundException(
                        "Reservation not found or already cancelled: " + id
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error cancelling reservation",
                    e
            );
        }
    }

    @Override
    public void delete(UUID id) {

    }
}