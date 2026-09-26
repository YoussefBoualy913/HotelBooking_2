package policy.pricing.total;

import policy.pricing.PricingContext;
import policy.pricing.TotalPricingRule;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class LastMinuteRule implements TotalPricingRule {

    private static final int MAX_DAYS_IN_ADVANCE = 3;

    private static final BigDecimal LAST_MINUTE_RATE =
            new BigDecimal("1.10");

    @Override
    public boolean applies(PricingContext context) {

        long daysBeforeCheckIn = ChronoUnit.DAYS.between(
                context.getBookingDate(),
                context.getCheckIn()
        );

        return daysBeforeCheckIn <= MAX_DAYS_IN_ADVANCE;
    }

    @Override
    public BigDecimal apply(
            BigDecimal total,
            PricingContext context) {

        return total.multiply(LAST_MINUTE_RATE);
    }
}
