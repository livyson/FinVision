package com.finvision.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@SuperBuilder
public class Transaction extends AuditableEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory category;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime date;

    @Column
    private Set<String> tags;

    @Column(nullable = false)
    private boolean reconciled;

    @Column(length = 1000)
    private String notes;

    public Transaction() {
        this.tags = new HashSet<>();
        this.reconciled = false;
        this.date = LocalDateTime.now();
    }

    public enum TransactionType {
        INCOME,
        EXPENSE,
        TRANSFER
    }

    public enum TransactionCategory {
        // Income Categories
        SALARY,
        INVESTMENT_INCOME,
        BUSINESS_INCOME,
        OTHER_INCOME,

        // Expense Categories
        FOOD_AND_DRINK,
        SHOPPING,
        HOUSING,
        TRANSPORTATION,
        UTILITIES,
        INSURANCE,
        HEALTHCARE,
        ENTERTAINMENT,
        PERSONAL_CARE,
        EDUCATION,
        TRAVEL,
        GIFTS_AND_DONATIONS,
        TAXES,
        OTHER_EXPENSE,

        // Transfer Categories
        TRANSFER_IN,
        TRANSFER_OUT
    }
} 