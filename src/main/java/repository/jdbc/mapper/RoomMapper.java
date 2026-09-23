package repository.jdbc.mapper;

import model.Room;
import model.enums.RoomStatus;
import model.enums.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RoomMapper {

    public static Room map(ResultSet rs) throws SQLException {

        return new Room(
                rs.getObject("id", UUID.class),
                rs.getString("room_number"),
                RoomType.valueOf(rs.getString("type")),
                rs.getInt("capacity"),
                rs.getBigDecimal("price"),
                RoomStatus.valueOf(rs.getString("status"))
        );
    }
}