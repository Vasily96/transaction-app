package org.example.repository;

import org.example.entity.CurrencyExchange;
import org.example.utils.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyExchange, Long> {
    public CurrencyExchange findCurrencyExchangeByFirstAndSecond(Currency first, Currency second);
}
