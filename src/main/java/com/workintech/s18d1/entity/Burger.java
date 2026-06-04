package com.workintech.s18d1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Burger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Boolean isVegan;

    @Enumerated(EnumType.STRING)
    private BreadType breadType;

    @Column(columnDefinition = "TEXT")
    private String contents;
}