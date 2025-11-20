package com.matheo.chahwan.proxibanque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private String lastName;
    private String firstName;
    private String address;
    private String postalCode;
    private String city;
    private String phone;

    private BigDecimal initialCurrentBalance;
    private BigDecimal overdraft;

    private BigDecimal initialSavingsBalance;
    private Double interestRate;

    private boolean addVisaElectron;
    private boolean addVisaPremier;

    private Long advisorId;
}
