package com.finvision.core.repository;

import com.finvision.core.domain.Budget;
import com.finvision.core.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByActiveTrue();

    Optional<Budget> findByIdAndActiveTrue(Long id);

    @Query("SELECT b FROM Budget b WHERE b.active = true AND b.month = :month")
    List<Budget> findByMonth(@Param("month") YearMonth month);

    @Query("SELECT b FROM Budget b WHERE b.active = true AND b.category = :category AND b.month = :month")
    Optional<Budget> findByCategoryAndMonth(
            @Param("category") Transaction.TransactionCategory category,
            @Param("month") YearMonth month
    );

    @Query("SELECT b FROM Budget b WHERE b.active = true AND b.month BETWEEN :startMonth AND :endMonth")
    List<Budget> findByMonthRange(
            @Param("startMonth") YearMonth startMonth,
            @Param("endMonth") YearMonth endMonth
    );

    boolean existsByCategoryAndMonthAndActiveTrue(
            Transaction.TransactionCategory category,
            YearMonth month
    );
} 