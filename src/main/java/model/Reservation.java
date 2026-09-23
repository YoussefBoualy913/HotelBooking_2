package model;
import model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private UUID id;
    private String reservationCode;
    private UUID userId;
    private UUID roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numberOfGuests;
    private int numberOfNights;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private LocalDateTime createdAt;

    public Reservation(
            UUID id,
            UUID userId,
            String reservationCode,
            UUID roomId,
            LocalDate checkIn,
            LocalDate checkOut,
            int numberOfGuests,
            int numberOfNights,
            BigDecimal totalPrice,
            ReservationStatus status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.reservationCode = reservationCode;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
        this.numberOfNights = numberOfNights;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
    public int getNumberOfNights() {
        return numberOfNights;
    }
    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Code: " + reservationCode +
                " | Room: " + roomId +
                " | Check-in: " + checkIn +
                " | Check-out: " + checkOut +
                " | Guests: " + numberOfGuests +
                " | Total: " + totalPrice + " MAD" +
                " | Status: " + status;
    }
}