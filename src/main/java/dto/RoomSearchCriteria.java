package dto;

import java.time.LocalDate;

public class RoomSearchCriteria {

    private LocalDate checkIn;
    private LocalDate checkOut;
    private int numberOfGuests;

    public RoomSearchCriteria(LocalDate checkIn, LocalDate checkOut, int numberOfGuests) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfGuests = numberOfGuests;
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
}