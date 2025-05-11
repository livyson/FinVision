package com.finvision.core.service.impl;

import com.finvision.core.domain.Account;
import com.finvision.core.domain.Budget;
import com.finvision.core.domain.Transaction;
import com.finvision.core.repository.BudgetRepository;
import com.finvision.core.service.BudgetService;
import com.finvision.core.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionService transactionService;

    @Override
    public Budget createBudget(Budget budget) {
        if (budgetRepository.existsByCategoryAndMonthAndActiveTrue(budget.getCategory(), budget.getMonth())) {
            throw new IllegalArgumentException("Budget already exists for this category and month");
        }
        return budgetRepository.save(budget);
    }

    @Override
    public Budget updateBudget(Long id, Budget budget) {
        Budget existingBudget = budgetRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        if (!existingBudget.getCategory().equals(budget.getCategory()) ||
                !existingBudget.getMonth().equals(budget.getMonth())) {
            if (budgetRepository.existsByCategoryAndMonthAndActiveTrue(budget.getCategory(), budget.getMonth())) {
                throw new IllegalArgumentException("Budget already exists for this category and month");
            }
        }

        existingBudget.setName(budget.getName());
        existingBudget.setDescription(budget.getDescription());
        existingBudget.setCategory(budget.getCategory());
        existingBudget.setMonth(budget.getMonth());
        existingBudget.setAmount(budget.getAmount());
        existingBudget.setActive(budget.isActive());

        return budgetRepository.save(existingBudget);
    }

    @Override
    public void deleteBudget(Long id) {
        Budget budget = budgetRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));
        budget.setActive(false);
        budgetRepository.save(budget);
    }

    @Override
    @Transactional(readOnly = true)
    public Budget getBudget(Long id) {
        return budgetRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Budget> getAllBudgets() {
        return budgetRepository.findByActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Budget> getBudgetsByMonth(YearMonth month) {
        return budgetRepository.findByMonth(month);
    }

    @Override
    @Transactional(readOnly = true)
    public Budget getBudgetByCategoryAndMonth(Transaction.TransactionCategory category, YearMonth month) {
        return budgetRepository.findByCategoryAndMonth(category, month)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Budget> getBudgetsByMonthRange(YearMonth startMonth, YearMonth endMonth) {
        return budgetRepository.findByMonthRange(startMonth, endMonth);
    }

    @Override
    public void updateBudgetSpent(Long budgetId, double amount) {
        Budget budget = budgetRepository.findByIdAndActiveTrue(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        BigDecimal currentSpent = budget.getSpent();
        BigDecimal newSpent = currentSpent.add(BigDecimal.valueOf(amount));
        budget.setSpent(newSpent);

        budgetRepository.save(budget);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBudgetExists(Transaction.TransactionCategory category, YearMonth month) {
        return budgetRepository.existsByCategoryAndMonthAndActiveTrue(category, month);
    }

    @Override
    public void recalculateBudgetSpent(Long budgetId) {
        Budget budget = budgetRepository.findByIdAndActiveTrue(budgetId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        // Get the start and end of the budget month
        LocalDateTime startDate = budget.getMonth().atDay(1).atStartOfDay();
        LocalDateTime endDate = budget.getMonth().atEndOfMonth().atTime(23, 59, 59);

        // Get all accounts to calculate total spent
        List<Account> accounts = transactionService.getTransactionsByAccountAndCategoryAndDateRange(
                null, // We'll need to modify the service to handle this case
                budget.getCategory(),
                startDate,
                endDate
        ).stream()
                .map(Transaction::getAccount)
                .distinct()
                .toList();

        double totalSpent = 0.0;
        for (Account account : accounts) {
            totalSpent += transactionService.getTotalAmountByAccountAndTypeAndDateRange(
                    account,
                    Transaction.TransactionType.EXPENSE,
                    startDate,
                    endDate
            );
        }

        budget.setSpent(BigDecimal.valueOf(totalSpent));
        budgetRepository.save(budget);
    }
} 