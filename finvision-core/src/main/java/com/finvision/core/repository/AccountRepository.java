package com.finvision.core.repository;

import com.finvision.core.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByActiveTrue();

    Optional<Account> findByIdAndActiveTrue(Long id);

    @Query("SELECT a FROM Account a WHERE a.active = true AND a.type = ?1")
    List<Account> findByType(Account.AccountType type);

    @Query("SELECT a FROM Account a WHERE a.active = true AND a.currency = ?1")
    List<Account> findByCurrency(String currency);

    boolean existsByNameAndActiveTrue(String name);
} 