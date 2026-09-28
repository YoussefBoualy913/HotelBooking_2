package repository.jdbc.mapper;

import model.Reservation;
import model.enums.ReservationStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ResrvationMapper {

    public static Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation(
                rs.getObject("id", UUID.class),
                rs.getObject("user_id", UUID.class),
                rs.getObject("room_id", UUID.class),
                rs.getDate("check_in").toLocalDate(),
                rs.getDate("check_out").toLocalDate(),
                rs.getInt("number_of_guests"),
                rs.getInt("number_of_nights"),
                ReservationStatus.valueOf(
                        rs.getString("status")
                )
        );
        reservation.setReservationCode(
                rs.getString("reservation_code")
        );

        reservation.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime()
        );
        return reservation;
    }
}
