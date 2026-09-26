package service;

import model.Payment;
import model.enums.PaymentMethod;
import model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentService {

    public Payment createPayment(
            UUID reservationId,
            BigDecimal amount,
            PaymentMethod method) {

        if (reservationId == null) {
            throw new IllegalArgumentException(
                    "Reservation ID cannot be null"
            );
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        if (method == null) {
            throw new IllegalArgumentException(
                    "Payment method cannot be null"
            );
        }

        return new Payment(
                UUID.randomUUID(),
                reservationId,
                amount,
                method,
                PaymentStatus.COMPLETED
        );
    }
}
