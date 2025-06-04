package com.homebudget.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "icons")
@Data
public class Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
}