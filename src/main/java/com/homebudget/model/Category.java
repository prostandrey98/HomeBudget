package com.homebudget.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "icon_id")
    private Icon icon;

    @Column(name = "is_default")
    private boolean isDefault;
}