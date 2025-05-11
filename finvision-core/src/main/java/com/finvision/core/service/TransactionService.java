package com.finvision.core.service;

import com.finvision.core.domain.Account;
import com.finvision.core.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    
    Transaction updateTransaction(Long id, Transaction transaction);
    
    void deleteTransaction(Long id);
    
    Transaction getTransaction(Long id);
    
    Page<Transaction> getTransactionsByAccount(Account account, Pageable pageable);
    
    List<Transaction> getTransactionsByAccountAndDateRange(
            Account account,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
    
    List<Transaction> getTransactionsByAccountAndTypeAndDateRange(
            Account account,
            Transaction.TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
    
    List<Transaction> getTransactionsByAccountAndCategoryAndDateRange(
            Account account,
            Transaction.TransactionCategory category,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
    
    double getTotalAmountByAccountAndTypeAndDateRange(
            Account account,
            Transaction.TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
    
    void reconcileTransaction(Long id);
    
    List<Transaction> getUnreconciledTransactions();
} 