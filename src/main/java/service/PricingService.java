package service;

import policy.pricing.DailyPricingRule;
import policy.pricing.PricingContext;
import policy.pricing.TotalPricingRule;
import policy.pricing.daily.HighSeasonRule;
import policy.pricing.daily.LowSeasonRule;
import policy.pricing.daily.WeekendRule;
import policy.pricing.total.EarlyBookingRule;
import policy.pricing.total.LastMinuteRule;
import policy.pricing.total.LongStayRule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PricingService {

    private final List<DailyPricingRule > dailyRules;
    private final List<TotalPricingRule> totalRules;

    public PricingService() {

        this.dailyRules = List.of(
                new HighSeasonRule(),
                new LowSeasonRule(),
                new WeekendRule()
        );

        this.totalRules = List.of(
                new LongStayRule(),
                new EarlyBookingRule(),
                new LastMinuteRule()
        );
    }
    public BigDecimal calculateTotalPrice(PricingContext context) {

        BigDecimal total = calculateDailyPrice(context);

        for (TotalPricingRule rule : totalRules) {
            if (rule.applies(context)) {
                total = rule.apply(total, context);
            }
        }

        return total;
    }

    private BigDecimal calculateDailyPrice(PricingContext context) {

        BigDecimal total = BigDecimal.ZERO;

        LocalDate currentDate = context.getCheckIn();

        while (currentDate.isBefore(context.getCheckOut())) {

            BigDecimal nightlyPrice = context.getPricePerNight();

            for (DailyPricingRule rule : dailyRules) {

                if (rule.applies(context, currentDate)) {
                    nightlyPrice =
                            rule.apply(nightlyPrice, context);
                }
            }

            total = total.add(nightlyPrice);

            currentDate = currentDate.plusDays(1);
        }

        return total;
    }
}