package org.example;

import lombok.Getter;
import lombok.Setter;

public class Log {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String symbol;
    @Getter
    @Setter
    private double price;

    public Log(String username, String symbol, double price) {
        this.username = username;
        this.symbol = symbol;
        this.price = price;
    }
}
