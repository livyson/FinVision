package com.finvision.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@SuperBuilder
public class Account extends AuditableEntity {

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @NotNull
    @Column(nullable = false)
    private String currency;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double balance;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean active;

    public Account() {
        this.active = true;
        this.balance = 0.0;
    }

    public enum AccountType {
        CHECKING,
        SAVINGS,
        CREDIT_CARD,
        INVESTMENT,
        LOAN,
        OTHER
    }
} 