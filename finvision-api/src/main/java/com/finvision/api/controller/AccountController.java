package com.finvision.api.controller;

import com.finvision.core.domain.Account;
import com.finvision.core.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management", description = "APIs for managing financial accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Create a new account")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    public ResponseEntity<Account> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody Account account
    ) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an account by ID")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return accountService.getAccount(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get accounts by type")
    public ResponseEntity<List<Account>> getAccountsByType(@PathVariable Account.AccountType type) {
        return ResponseEntity.ok(accountService.getAccountsByType(type));
    }

    @GetMapping("/currency/{currency}")
    @Operation(summary = "Get accounts by currency")
    public ResponseEntity<List<Account>> getAccountsByCurrency(@PathVariable String currency) {
        return ResponseEntity.ok(accountService.getAccountsByCurrency(currency));
    }

    @GetMapping("/check-name")
    @Operation(summary = "Check if an account name is unique")
    public ResponseEntity<Boolean> isAccountNameUnique(@RequestParam String name) {
        return ResponseEntity.ok(accountService.isAccountNameUnique(name));
    }
} 