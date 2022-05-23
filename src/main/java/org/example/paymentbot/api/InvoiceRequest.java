package org.example.paymentbot.api;
import com.jsoniter.any.Any;
import lombok.Data;

import javax.swing.*;
import java.math.BigDecimal;

@Data
public class InvoiceRequest {
    private BigDecimal price;
    private String store_id;
    private String currency;
    private String promocode;
    private String shipping_address;
    private Any products;
}
