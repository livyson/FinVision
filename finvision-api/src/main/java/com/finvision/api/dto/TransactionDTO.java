package com.finvision.api.dto;

import com.finvision.core.domain.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record TransactionDTO(
        Long id,
        @NotNull(message = "Account ID is required")
        Long accountId,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Type is required")
        Transaction.TransactionType type,
        @NotNull(message = "Category is required")
        Transaction.TransactionCategory category,
        @NotNull(message = "Date is required")
        @PastOrPresent(message = "Date cannot be in the future")
        LocalDateTime date,
        Set<String> tags,
        boolean reconciled,
        String notes
) {
    public static TransactionDTO fromEntity(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getTags(),
                transaction.isReconciled(),
                transaction.getNotes()
        );
    }

    public Transaction toEntity() {
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setCategory(category);
        transaction.setDate(date);
        transaction.setTags(tags);
        transaction.setReconciled(reconciled);
        transaction.setNotes(notes);
        return transaction;
    }
} 