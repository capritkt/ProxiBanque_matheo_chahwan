package com.matheo.chahwan.proxibanque.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CURRENT")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CurrentAccount extends Account {
    private BigDecimal overdraft;
}