package com.matheo.chahwan.proxibanque.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Agency {
    @Id
    private String id;
    private String creationDate;
    private String name;
}
