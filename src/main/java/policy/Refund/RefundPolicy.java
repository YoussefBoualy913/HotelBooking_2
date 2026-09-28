package policy.Refund;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class RefundPolicy {

    public static BigDecimal calculateRefund(
            BigDecimal totalAmount,
            LocalDateTime checkIn
    ) {

        long hoursBeforeCheckIn =
                Duration.between(LocalDateTime.now(), checkIn).toHours();

        if (hoursBeforeCheckIn > 14 * 24) {
            return totalAmount;
        }

        if (hoursBeforeCheckIn >= 7 * 24) {
            return totalAmount.multiply(new BigDecimal("0.70"));
        }

        if (hoursBeforeCheckIn >= 48) {
            return totalAmount.multiply(new BigDecimal("0.50"));
        }

        return BigDecimal.ZERO;
    }
}
