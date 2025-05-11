package com.finvision.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@SuperBuilder
public class Budget extends AuditableEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Transaction.TransactionCategory category;

    @NotNull
    @Column(nullable = false)
    private YearMonth month;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal spent;

    @Column(nullable = false)
    private boolean active;

    public Budget() {
        this.active = true;
        this.spent = BigDecimal.ZERO;
    }

    public BigDecimal getRemaining() {
        return amount.subtract(spent);
    }

    public BigDecimal getPercentageUsed() {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return spent.divide(amount, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }
} 