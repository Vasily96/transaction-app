package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.example.utils.enums.ClientsType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "client_type")
    @Enumerated(EnumType.STRING)
    private ClientsType type;
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();


}
