package service;

import model.Invoice;

import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceService {

    private static final BigDecimal VAT_RATE =
            new BigDecimal("0.20");

    public Invoice createInvoice(BigDecimal totalHT) {

        BigDecimal vat = totalHT.multiply(VAT_RATE);

        BigDecimal totalTTC = totalHT.add(vat);

        return new Invoice(
                UUID.randomUUID(),
                totalHT,
                VAT_RATE,
                vat,
                totalTTC
        );
    }
}
