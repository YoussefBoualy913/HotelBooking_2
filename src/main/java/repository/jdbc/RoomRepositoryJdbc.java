package repository.jdbc;

import db.DatabaseConnection;
import dto.AvailableRoomDTO;
import dto.RoomSearchCriteria;
import model.Room;
import repository.RoomRepository;
import repository.jdbc.mapper.AvailableRoomMapper;
import repository.jdbc.mapper.RoomMapper;
import repository.jdbc.mapper.UserMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoomRepositoryJdbc implements RoomRepository {

    private final Connection connection;
    public RoomRepositoryJdbc() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Room save(Room room){
        String sql= """
                insert into rooms(id,room_number,type,capacity,price, status) values(?,?,?,?,?,?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, room.getId());
            ps.setString(2, room.getRoomNumber());
            ps.setString(3, room.getType().name());
            ps.setInt(4, room.getCapacity());
            ps.setObject(5, room.getPricePerNight());
            ps.setString(6, room.getStatus().name());

            ps.executeUpdate();
            return room;
        }catch (SQLException e){
            throw new RuntimeException(
                    "Error creating room", e
            );
        }
    }
    @Override
    public Optional<Room> findByRoomNumber(String roomNumber){
        String sql = """
            SELECT id, room_number,type, capacity, price,
                   status
            FROM rooms
            WHERE room_number = ?
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, roomNumber);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(RoomMapper.map(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error finding room by roomNumber", e
            );
        }

        return Optional.empty();
    }
    @Override
    public List<Room> findAll(){
        String sql = """
        SELECT id, room_number, type, capacity, price, status
        FROM rooms
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)){
          try(ResultSet rs = ps.executeQuery()) {
              List<Room> rooms = new ArrayList<>();
              while (rs.next()){
                rooms.add(RoomMapper.map(rs));
              }
              return rooms;
          }
        } catch (
    SQLException e) {
        throw new RuntimeException(
                "Erreur lors  de chargement des room", e
        );
    }
    }

    @Override
    public void update(Room room){
        String sql = """
            UPDATE rooms set type = ?, price = ?, capacity = ? WHERE id = ?
        """;

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,room.getType().name());
            ps.setObject(2,room.getPricePerNight());
            ps.setInt(3,room.getCapacity());
            ps.setObject(4,room.getId());

            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException( "Erreur lors  de modéfication de room", e);
        }
    }
    public void putRoomInMaintenance(Room room){
        String sql = """
            UPDATE rooms set status = ? where id = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, room.getStatus().name());
            ps.setObject(2, room.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Erreur lors  de modification de room", e);
        }
    }

    public List<AvailableRoomDTO> findAvailableRooms(RoomSearchCriteria roomSearchCriteria){
        String sql = """
            SELECT r.id,
                   r.room_number,
                   r.type,
                   r.capacity,
                   r.price,
                   r.status
                   FROM rooms r
                   WHERE r.status = 'AVAILABLE'
                        AND r.capacity >= ?
                        AND NOT EXISTS (
                            SELECT 1
                            FROM reservations res
                            WHERE res.room_id = r.id
                                 AND res.status = 'CONFIRMED'
                                 AND res.check_in < ?
                                 AND res.check_out > ?
                    );
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, roomSearchCriteria.getNumberOfGuests());
            ps.setDate(2, Date.valueOf(roomSearchCriteria.getCheckOut()));
            ps.setDate(3, Date.valueOf(roomSearchCriteria.getCheckIn()));
            List<AvailableRoomDTO> availableRoomsDTO = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    availableRoomsDTO.add(AvailableRoomMapper.map(rs,roomSearchCriteria));
                }
            }
           return availableRoomsDTO;
        }catch (SQLException e){
            throw new RuntimeException("Erreur lors  de chargement de room", e);
        }
    }

    @Override
    public boolean isRoomAvailable(
            UUID roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        String sql = """
        SELECT NOT EXISTS (
            SELECT 1
            FROM reservations
            WHERE room_id = ?
              AND status = 'CONFIRMED'
              AND check_in < ?
              AND check_out > ?
        )
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, roomId);
            ps.setDate(2, Date.valueOf(checkOut));
            ps.setDate(3, Date.valueOf(checkIn));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error checking room availability", e
            );
        }

        return false;
    }
    }
