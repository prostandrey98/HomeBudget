package com.homebudget.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class FutureExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urgency;

    private String importance;

    private String category;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE") // Явно указываем значение по умолчанию в схеме
    private boolean recurring = false; // Значение по умолчанию в коде

    private String frequency;

    private double estimatedCost;

    private LocalDate date;

    private String title;

    public FutureExpense() {}

    public FutureExpense(String urgency, String importance, String category, boolean recurring, String frequency, double estimatedCost, LocalDate date) {
        this.urgency = urgency;
        this.importance = importance;
        this.category = category;
        this.recurring = recurring;
        this.frequency = frequency;
        this.estimatedCost = estimatedCost;
        this.date = date;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FutureExpense{" +
                "id=" + id +
                ", urgency='" + urgency + '\'' +
                ", importance='" + importance + '\'' +
                ", category='" + category + '\'' +
                ", recurring=" + recurring +
                ", frequency='" + frequency + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", date=" + date +
                ", title='" + title + '\'' +
                '}';
    }
}