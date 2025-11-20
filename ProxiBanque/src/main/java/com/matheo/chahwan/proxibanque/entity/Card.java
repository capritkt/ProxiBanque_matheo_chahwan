package com.matheo.chahwan.proxibanque.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardType; // Electron ou Premier
    private boolean active;

    @ManyToOne
    @JsonBackReference(value = "client-cards")
    private Client owner;
}