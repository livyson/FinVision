package com.finvision.api.dto;

import com.finvision.core.domain.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AccountDTO(
        Long id,
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,
        @NotNull(message = "Type is required")
        Account.AccountType type,
        @NotNull(message = "Currency is required")
        String currency,
        @NotNull(message = "Balance is required")
        @Positive(message = "Balance must be positive")
        Double balance,
        String description,
        boolean active
) {
    public static AccountDTO fromEntity(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getName(),
                account.getType(),
                account.getCurrency(),
                account.getBalance(),
                account.getDescription(),
                account.isActive()
        );
    }

    public Account toEntity() {
        Account account = new Account();
        account.setName(name);
        account.setType(type);
        account.setCurrency(currency);
        account.setBalance(balance);
        account.setDescription(description);
        account.setActive(active);
        return account;
    }
} 