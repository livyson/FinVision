package com.finvision.api.controller;

import com.finvision.core.domain.Budget;
import com.finvision.core.domain.Transaction;
import com.finvision.core.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget Management", description = "APIs for managing financial budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "Create a new budget")
    public ResponseEntity<Budget> createBudget(@Valid @RequestBody Budget budget) {
        return ResponseEntity.ok(budgetService.createBudget(budget));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing budget")
    public ResponseEntity<Budget> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody Budget budget
    ) {
        return ResponseEntity.ok(budgetService.updateBudget(id, budget));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a budget")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a budget by ID")
    public ResponseEntity<Budget> getBudget(@PathVariable Long id) {
        return ResponseEntity.ok(budgetService.getBudget(id));
    }

    @GetMapping
    @Operation(summary = "Get all budgets")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    @GetMapping("/month/{month}")
    @Operation(summary = "Get budgets for a specific month")
    public ResponseEntity<List<Budget>> getBudgetsByMonth(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month
    ) {
        return ResponseEntity.ok(budgetService.getBudgetsByMonth(month));
    }

    @GetMapping("/category/{category}/month/{month}")
    @Operation(summary = "Get budget for a specific category and month")
    public ResponseEntity<Budget> getBudgetByCategoryAndMonth(
            @PathVariable Transaction.TransactionCategory category,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month
    ) {
        return ResponseEntity.ok(budgetService.getBudgetByCategoryAndMonth(category, month));
    }

    @GetMapping("/month-range")
    @Operation(summary = "Get budgets for a range of months")
    public ResponseEntity<List<Budget>> getBudgetsByMonthRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth startMonth,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth endMonth
    ) {
        return ResponseEntity.ok(budgetService.getBudgetsByMonthRange(startMonth, endMonth));
    }

    @PostMapping("/{id}/update-spent")
    @Operation(summary = "Update the spent amount for a budget")
    public ResponseEntity<Void> updateBudgetSpent(
            @PathVariable Long id,
            @RequestParam double amount
    ) {
        budgetService.updateBudgetSpent(id, amount);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-exists")
    @Operation(summary = "Check if a budget exists for a category and month")
    public ResponseEntity<Boolean> isBudgetExists(
            @RequestParam Transaction.TransactionCategory category,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month
    ) {
        return ResponseEntity.ok(budgetService.isBudgetExists(category, month));
    }

    @PostMapping("/{id}/recalculate")
    @Operation(summary = "Recalculate the spent amount for a budget")
    public ResponseEntity<Void> recalculateBudgetSpent(@PathVariable Long id) {
        budgetService.recalculateBudgetSpent(id);
        return ResponseEntity.noContent().build();
    }
} 