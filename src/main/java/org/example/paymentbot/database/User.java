package org.example.paymentbot.database;

import lombok.Value;

@Value
public class User {
    long id;
    int domain;
    String name;
}
