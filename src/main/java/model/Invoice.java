package model;

import java.math.BigDecimal;
import java.util.UUID;

public class Invoice {
    private UUID id;
    private UUID reservationId;
    private String invoiceNumber;
    private BigDecimal subtotalHT;
    private BigDecimal vat;
    private BigDecimal totalTTC;

    public Invoice(UUID  reservationId,
                   String invoiceNumber,
                   BigDecimal subtotalHT,
                   BigDecimal vat,
                   BigDecimal totalTTC) {
        this.id = UUID.randomUUID();
        this.reservationId = reservationId;
        this.invoiceNumber = invoiceNumber;
        this.subtotalHT = subtotalHT;
        this.vat = vat;
        this.totalTTC = totalTTC;
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
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public BigDecimal getSubtotalHT() {
        return subtotalHT;
    }
    public void setSubtotalHT(BigDecimal subtotalHT) {
        this.subtotalHT = subtotalHT;
    }
    public BigDecimal getVat() {
        return vat;
    }
    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }
    public BigDecimal getTotalTTC() {
        return totalTTC;
    }
    public void setTotalTTC(BigDecimal totalTTC) {
        this.totalTTC = totalTTC;
    }
    @Override
    public String toString() {
        return "";
    }
}