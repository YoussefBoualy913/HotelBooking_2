package repository;

import dto.AvailableRoomDTO;
import dto.RoomSearchCriteria;
import model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findAll();
    void update(Room room);
    void putRoomInMaintenance(Room room);
    List<AvailableRoomDTO> findAvailableRooms(RoomSearchCriteria roomSearchCriteria);
    boolean isRoomAvailable(UUID roomNumber, LocalDate checkIn, LocalDate checkOut);
   // void delete(Room room);
}