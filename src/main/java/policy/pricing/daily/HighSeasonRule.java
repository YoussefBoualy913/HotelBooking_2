package policy.pricing.daily;

import policy.pricing.DailyPricingRule;
import policy.pricing.PricingContext;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HighSeasonRule implements DailyPricingRule {

    private static final BigDecimal HIGH_SEASON_RATE =
            new BigDecimal("1.30");

    @Override
    public boolean applies(
            PricingContext context,
            LocalDate date) {

        int month = date.getMonthValue();

        return month == 7 || month == 8;
    }

    @Override
    public BigDecimal apply(
            BigDecimal price,
            PricingContext context) {

        return price.multiply(HIGH_SEASON_RATE);
    }
}
