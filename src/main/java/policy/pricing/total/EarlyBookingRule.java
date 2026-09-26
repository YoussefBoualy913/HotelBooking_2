package policy.pricing.total;

import policy.pricing.PricingContext;
import policy.pricing.TotalPricingRule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class EarlyBookingRule implements TotalPricingRule {

    private static final int MIN_DAYS_IN_ADVANCE = 30;

    private static final BigDecimal EARLY_BOOKING_RATE =
            new BigDecimal("0.95");

    @Override
    public boolean applies(PricingContext context) {

        long daysBeforeCheckIn = ChronoUnit.DAYS.between(
                context.getBookingDate(),
                context.getCheckIn()
        );

        return daysBeforeCheckIn >= MIN_DAYS_IN_ADVANCE;
    }

    @Override
    public BigDecimal apply(
            BigDecimal total,
            PricingContext context) {

        return total.multiply(EARLY_BOOKING_RATE);
    }
}
