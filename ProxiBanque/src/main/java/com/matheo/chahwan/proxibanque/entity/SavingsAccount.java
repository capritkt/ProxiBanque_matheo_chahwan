package com.matheo.chahwan.proxibanque.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("SAVINGS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SavingsAccount extends Account {
    private Double interestRate;
}