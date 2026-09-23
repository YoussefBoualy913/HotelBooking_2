package repository;

import model.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findAll();
    void update(Room room);
    void putRoomInMaintenance(Room room);
    List<Room> findAvailableRooms(int finalGuestsNumber, LocalDate checkIn,LocalDate checkOut);
   // void delete(Room room);
}