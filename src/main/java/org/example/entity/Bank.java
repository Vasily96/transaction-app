package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "bank")
    @ToString.Exclude
    @Fetch(FetchMode.SUBSELECT)
    private List<Account> accounts = new ArrayList<>();
    private Double individualCommission;
    private Double legalCommission;
}
