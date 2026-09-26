package policy.pricing;

import java.math.BigDecimal;

public interface TotalPricingRule {

    boolean applies(PricingContext context);

    BigDecimal apply(BigDecimal price, PricingContext context);
}