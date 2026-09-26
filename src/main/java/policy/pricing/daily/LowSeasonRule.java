package policy.pricing.daily;

import policy.pricing.DailyPricingRule;
import policy.pricing.PricingContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class LowSeasonRule implements DailyPricingRule {

    private static final BigDecimal LOW_SEASON_RATE =
            new BigDecimal("0.85");

    @Override
    public boolean applies(
            PricingContext context,
            LocalDate date) {

        return date.getMonth() == Month.NOVEMBER
                || date.getMonth() == Month.DECEMBER
                || date.getMonth() == Month.JANUARY
                || date.getMonth() == Month.FEBRUARY;
    }

    @Override
    public BigDecimal apply(
            BigDecimal price,
            PricingContext context) {

        return price.multiply(LOW_SEASON_RATE);
    }
}