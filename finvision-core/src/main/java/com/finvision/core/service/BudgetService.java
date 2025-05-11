package com.finvision.core.service;

import com.finvision.core.domain.Budget;
import com.finvision.core.domain.Transaction;

import java.time.YearMonth;
import java.util.List;

public interface BudgetService {
    Budget createBudget(Budget budget);
    
    Budget updateBudget(Long id, Budget budget);
    
    void deleteBudget(Long id);
    
    Budget getBudget(Long id);
    
    List<Budget> getAllBudgets();
    
    List<Budget> getBudgetsByMonth(YearMonth month);
    
    Budget getBudgetByCategoryAndMonth(Transaction.TransactionCategory category, YearMonth month);
    
    List<Budget> getBudgetsByMonthRange(YearMonth startMonth, YearMonth endMonth);
    
    void updateBudgetSpent(Long budgetId, double amount);
    
    boolean isBudgetExists(Transaction.TransactionCategory category, YearMonth month);
    
    void recalculateBudgetSpent(Long budgetId);
} 