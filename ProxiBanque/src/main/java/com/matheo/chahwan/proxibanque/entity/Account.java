package com.matheo.chahwan.proxibanque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public abstract class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String number;

    private BigDecimal balance;
    private LocalDate openingDate;

    @ManyToOne
    @JsonBackReference(value = "client-accounts")
    private Client owner;
}