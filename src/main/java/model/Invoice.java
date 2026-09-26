package model;

import java.math.BigDecimal;
import java.util.UUID;

public class Invoice {
    private UUID id;
    private UUID paymentId;
    private String invoiceNumber;
    private BigDecimal totalHT;
    private BigDecimal vatRate;
    private BigDecimal vatAmount;
    private BigDecimal totalTTC;

    public Invoice(UUID id,
                   BigDecimal totalHT,
                   BigDecimal vatRate,
                   BigDecimal vatAmount,
                   BigDecimal totalTTC) {
        this.id =id;
        this.totalHT = totalHT;
        this.vatRate = vatRate;
        this.vatAmount = vatAmount;
        this.totalTTC = totalTTC;

    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(UUID reservationId) {
        this.paymentId = reservationId;
    }
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public BigDecimal getTotalHT() {
        return totalHT;
    }
    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }
    public BigDecimal getVatAmount() {
        return vatAmount;
    }
    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }
    public BigDecimal getTotalTTC() {
        return totalTTC;
    }
    public void setTotalTTC(BigDecimal totalTTC) {
        this.totalTTC = totalTTC;
    }
    public BigDecimal getVatRate() {
        return vatRate;
    }
    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }


    @Override
    public String toString() {
        return "";
    }
}