package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.utils.enums.Currency;

@Entity
@Table(name = "exchange")
@Data
public class CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_currency")
    @Enumerated(EnumType.STRING)
    private Currency first;
    @Column(name = "second_currency")
    @Enumerated(EnumType.STRING)
    private Currency second;
    @Column(name = "currency_rate")
    private Double rate;
}
