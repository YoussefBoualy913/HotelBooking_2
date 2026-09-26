package policy.pricing.total;

import policy.pricing.PricingContext;
import policy.pricing.TotalPricingRule;

import java.math.BigDecimal;

public class LongStayRule implements TotalPricingRule {

    private static final BigDecimal DISCOUNT_7_NIGHTS =
            new BigDecimal("0.90");

    private static final BigDecimal DISCOUNT_14_NIGHTS =
            new BigDecimal("0.85");

    @Override
    public boolean applies(PricingContext context) {
        return context.getNumberOfNights() >= 7;
    }

    @Override
    public BigDecimal apply(
            BigDecimal total,
            PricingContext context) {

        if (context.getNumberOfNights() >= 14) {
            return total.multiply(DISCOUNT_14_NIGHTS);
        }

        return total.multiply(DISCOUNT_7_NIGHTS);
    }
}