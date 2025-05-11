package com.finvision.core.service.impl;

import com.finvision.core.domain.Account;
import com.finvision.core.domain.Transaction;
import com.finvision.core.repository.TransactionRepository;
import com.finvision.core.service.AccountService;
import com.finvision.core.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        // Update account balance
        double amount = transaction.getAmount().doubleValue();
        if (transaction.getType() == Transaction.TransactionType.EXPENSE) {
            amount = -amount;
        }
        accountService.updateBalance(transaction.getAccount().getId(), amount);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        // Revert the old transaction's effect on account balance
        double oldAmount = existingTransaction.getAmount().doubleValue();
        if (existingTransaction.getType() == Transaction.TransactionType.EXPENSE) {
            oldAmount = -oldAmount;
        }
        accountService.updateBalance(existingTransaction.getAccount().getId(), -oldAmount);

        // Apply the new transaction's effect on account balance
        double newAmount = transaction.getAmount().doubleValue();
        if (transaction.getType() == Transaction.TransactionType.EXPENSE) {
            newAmount = -newAmount;
        }
        accountService.updateBalance(transaction.getAccount().getId(), newAmount);

        // Update transaction details
        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setType(transaction.getType());
        existingTransaction.setCategory(transaction.getCategory());
        existingTransaction.setTransactionDate(transaction.getTransactionDate());
        existingTransaction.setReferenceNumber(transaction.getReferenceNumber());
        existingTransaction.setNotes(transaction.getNotes());

        return transactionRepository.save(existingTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        // Revert the transaction's effect on account balance
        double amount = transaction.getAmount().doubleValue();
        if (transaction.getType() == Transaction.TransactionType.EXPENSE) {
            amount = -amount;
        }
        accountService.updateBalance(transaction.getAccount().getId(), -amount);

        transactionRepository.delete(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> getTransactionsByAccount(Account account, Pageable pageable) {
        return transactionRepository.findByAccount(account, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountAndDateRange(
            Account account,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return transactionRepository.findByAccountAndDateRange(account, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountAndTypeAndDateRange(
            Account account,
            Transaction.TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return transactionRepository.findByAccountAndTypeAndDateRange(account, type, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountAndCategoryAndDateRange(
            Account account,
            Transaction.TransactionCategory category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return transactionRepository.findByAccountAndCategoryAndDateRange(account, category, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public double getTotalAmountByAccountAndTypeAndDateRange(
            Account account,
            Transaction.TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        Double total = transactionRepository.sumAmountByAccountAndTypeAndDateRange(account, type, startDate, endDate);
        return total != null ? total : 0.0;
    }

    @Override
    public void reconcileTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
        transaction.setReconciled(true);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getUnreconciledTransactions() {
        return transactionRepository.findByReconciledFalse();
    }
} 