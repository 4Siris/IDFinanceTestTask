package org.example.repository;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Класс хранения данных из базы данных
@Entity
public class Currency {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    private int cid;
    @Getter
    @Setter
    private String symbol;
    @Getter     
    @Setter
    private double price;

    public Currency(){}
    public Currency(int cid, String symbol, double price) {
        this.cid = cid;
        this.symbol = symbol;
        this.price = price;
    }
}
