package policy.pricing.daily;

import policy.pricing.DailyPricingRule;
import policy.pricing.PricingContext;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendRule implements DailyPricingRule {

    @Override
    public boolean applies(
            PricingContext context,
            LocalDate date) {

        DayOfWeek day = date.getDayOfWeek();

        return day == DayOfWeek.FRIDAY
                || day == DayOfWeek.SATURDAY;
    }

    @Override
    public BigDecimal apply(
            BigDecimal price,
            PricingContext context) {

        return price.multiply(new BigDecimal("1.15"));
    }
}
