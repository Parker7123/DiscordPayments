package org.example.paymentbot.database;

import lombok.Data;
import lombok.Value;

@Value
public class Domain {
    private String apiUrl;
    private int id;
}
