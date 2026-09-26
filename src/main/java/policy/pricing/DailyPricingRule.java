package policy.pricing;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DailyPricingRule {
    boolean applies(PricingContext context, LocalDate date);

    BigDecimal apply(BigDecimal price, PricingContext context);
}
