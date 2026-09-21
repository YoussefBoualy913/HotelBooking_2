package model;

import model.enums.PaymentMethod;
import model.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID reservationId;
    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment(UUID id,
                   UUID reservationId
            , BigDecimal amount,
                   PaymentMethod method,
                   PaymentStatus status)
        {
        this.id = UUID.randomUUID();
        this.reservationId = reservationId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getReservationId() {
        return reservationId;
    }
    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public PaymentMethod getMethod() {
        return method;
    }
    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
    public PaymentStatus getStatus() {
        return status;
    }
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}