package com.finvision.core.repository;

import com.finvision.core.domain.Account;
import com.finvision.core.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByAccount(Account account, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.account = :account AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountAndDateRange(
            @Param("account") Account account,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT t FROM Transaction t WHERE t.account = :account AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountAndTypeAndDateRange(
            @Param("account") Account account,
            @Param("type") Transaction.TransactionType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT t FROM Transaction t WHERE t.account = :account AND t.category = :category AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountAndCategoryAndDateRange(
            @Param("account") Account account,
            @Param("category") Transaction.TransactionCategory category,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account = :account AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate")
    Double sumAmountByAccountAndTypeAndDateRange(
            @Param("account") Account account,
            @Param("type") Transaction.TransactionType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    List<Transaction> findByReconciledFalse();
} 