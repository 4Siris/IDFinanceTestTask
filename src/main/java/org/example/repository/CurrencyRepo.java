package org.example.repository;

import org.springframework.data.repository.CrudRepository;

//Интерфейс для запроса в базу данных
public interface CurrencyRepo extends CrudRepository<Currency,Long>{
    Currency findBySymbol(String symbol);
}