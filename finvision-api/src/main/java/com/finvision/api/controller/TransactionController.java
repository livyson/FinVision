package com.finvision.api.controller;

import com.finvision.core.domain.Account;
import com.finvision.core.domain.Transaction;
import com.finvision.core.service.AccountService;
import com.finvision.core.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "APIs for managing financial transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Create a new transaction")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing transaction")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody Transaction transaction
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, transaction));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a transaction by ID")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transactions for an account")
    public ResponseEntity<Page<Transaction>> getTransactionsByAccount(
            @PathVariable Long accountId,
            Pageable pageable
    ) {
        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(account, pageable));
    }

    @GetMapping("/account/{accountId}/date-range")
    @Operation(summary = "Get transactions for an account within a date range")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountAndDateRange(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByAccountAndDateRange(
                account, startDate, endDate));
    }

    @GetMapping("/account/{accountId}/type/{type}/date-range")
    @Operation(summary = "Get transactions for an account by type within a date range")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountAndTypeAndDateRange(
            @PathVariable Long accountId,
            @PathVariable Transaction.TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByAccountAndTypeAndDateRange(
                account, type, startDate, endDate));
    }

    @GetMapping("/account/{accountId}/category/{category}/date-range")
    @Operation(summary = "Get transactions for an account by category within a date range")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountAndCategoryAndDateRange(
            @PathVariable Long accountId,
            @PathVariable Transaction.TransactionCategory category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return ResponseEntity.ok(transactionService.getTransactionsByAccountAndCategoryAndDateRange(
                account, category, startDate, endDate));
    }

    @GetMapping("/account/{accountId}/total/{type}/date-range")
    @Operation(summary = "Get total amount for an account by type within a date range")
    public ResponseEntity<Double> getTotalAmountByAccountAndTypeAndDateRange(
            @PathVariable Long accountId,
            @PathVariable Transaction.TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Account account = accountService.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return ResponseEntity.ok(transactionService.getTotalAmountByAccountAndTypeAndDateRange(
                account, type, startDate, endDate));
    }

    @PostMapping("/{id}/reconcile")
    @Operation(summary = "Mark a transaction as reconciled")
    public ResponseEntity<Void> reconcileTransaction(@PathVariable Long id) {
        transactionService.reconcileTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unreconciled")
    @Operation(summary = "Get all unreconciled transactions")
    public ResponseEntity<List<Transaction>> getUnreconciledTransactions() {
        return ResponseEntity.ok(transactionService.getUnreconciledTransactions());
    }
} 