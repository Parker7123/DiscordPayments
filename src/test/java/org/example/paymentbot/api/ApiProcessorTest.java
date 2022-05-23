package org.example.paymentbot.api;

import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiProcessorTest {

    @Test
    void sendInvoiceRequest() {
        InvoiceRequest request = new InvoiceRequest();
        request.setCurrency("USD");
        request.setPrice(new BigDecimal(1));
        request.setStore_id("lZtXVdDklhmtcRSKIsDeVqHgKWXFQCFJ");
        request.setShipping_address("");
        request.setPromocode(null);
        Map<String,Integer> map = new HashMap<>();
        map.put("YltTcuIhYHtJqcMuVbIbkq",1);
        request.setProducts(Any.wrap(map));
        System.out.println(JsonStream.serialize(request));
        System.out.println( new ApiProcessor().createInvoice(request));
    }
}