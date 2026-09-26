package policy.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PricingContext {

    private final LocalDate bookingDate;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final BigDecimal pricePerNight;

    public PricingContext(
            LocalDate bookingDate,
            LocalDate checkIn,
            LocalDate checkOut,
            BigDecimal pricePerNight) {

        this.bookingDate = bookingDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pricePerNight = pricePerNight;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public long getNumberOfNights() {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }
}
