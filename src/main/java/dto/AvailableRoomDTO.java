package dto;

import model.enums.RoomType;

import java.math.BigDecimal;
import java.util.UUID;

public class AvailableRoomDTO {

    private UUID id;
    private String roomNumber;
    private RoomType type;
    private int capacity;
    private BigDecimal price;
    private BigDecimal totalprice;
    public AvailableRoomDTO(
            UUID id,
            String roomNumber,
            RoomType type,
            int capacity,
            BigDecimal price,
            BigDecimal totalprice) {

        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.totalprice = totalprice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber +
                " | Type: " + type +
                " | Capacity: " + capacity +
                " | Price: " + price + " MAD/night"+
                " | totalPrice: " + totalprice + " MAD";
    }
}