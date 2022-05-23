package org.example.paymentbot.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceCoin {
    private String amount;
    private String symbol;
    private String payment_address;
    private BigDecimal getAmount(){
        return new BigDecimal(amount);
    }
}
