package com.finvision.core.service;

import com.finvision.core.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account);
    
    Account updateAccount(Long id, Account account);
    
    void deleteAccount(Long id);
    
    Optional<Account> getAccount(Long id);
    
    List<Account> getAllAccounts();
    
    List<Account> getAccountsByType(Account.AccountType type);
    
    List<Account> getAccountsByCurrency(String currency);
    
    void updateBalance(Long accountId, double amount);
    
    boolean isAccountNameUnique(String name);
} 