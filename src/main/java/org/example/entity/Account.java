package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.utils.enums.Currency;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    @ToString.Exclude
    private Bank bank;
    @Column(name = "account_value")
    private Double value;
    @Column(name = "currency_account")
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", client=" + client.getName() +
                ", bank=" + bank.getName() +
                ", value=" + value +
                ", currency=" + currency +
                '}';
    }
}
