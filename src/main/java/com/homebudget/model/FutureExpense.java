package com.homebudget.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "future_expenses")
@Data
public class FutureExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Column(name = "is_recurring")
    private boolean isRecurring;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Column(name = "estimated_cost")
    private BigDecimal estimatedCost;

    public void setIsRecurring(boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public enum Urgency { URGENT, NOT_URGENT }
    public enum Importance { IMPORTANT, NOT_IMPORTANT }
    public enum Frequency { WEEKLY, MONTHLY, BIMONTHLY, QUARTERLY, YEARLY }
}