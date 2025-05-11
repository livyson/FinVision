package com.finvision.api.dto;

import com.finvision.core.domain.Budget;
import com.finvision.core.domain.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.YearMonth;

public record BudgetDTO(
        Long id,
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Category is required")
        Transaction.TransactionCategory category,
        @NotNull(message = "Month is required")
        YearMonth month,
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Spent amount is required")
        BigDecimal spent,
        String description,
        boolean active
) {
    public static BudgetDTO fromEntity(Budget budget) {
        return new BudgetDTO(
                budget.getId(),
                budget.getName(),
                budget.getCategory(),
                budget.getMonth(),
                budget.getAmount(),
                budget.getSpent(),
                budget.getDescription(),
                budget.isActive()
        );
    }

    public Budget toEntity() {
        Budget budget = new Budget();
        budget.setName(name);
        budget.setCategory(category);
        budget.setMonth(month);
        budget.setAmount(amount);
        budget.setSpent(spent);
        budget.setDescription(description);
        budget.setActive(active);
        return budget;
    }
} 