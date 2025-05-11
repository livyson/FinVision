package com.finvision.core.service.impl;

import com.finvision.core.domain.Account;
import com.finvision.core.repository.AccountRepository;
import com.finvision.core.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        if (accountRepository.existsByNameAndActiveTrue(account.getName())) {
            throw new IllegalArgumentException("Account name already exists");
        }
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long id, Account account) {
        Account existingAccount = accountRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if (!existingAccount.getName().equals(account.getName()) &&
                accountRepository.existsByNameAndActiveTrue(account.getName())) {
            throw new IllegalArgumentException("Account name already exists");
        }

        existingAccount.setName(account.getName());
        existingAccount.setDescription(account.getDescription());
        existingAccount.setType(account.getType());
        existingAccount.setCurrency(account.getCurrency());
        existingAccount.setActive(account.isActive());

        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        account.setActive(false);
        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getAccount(Long id) {
        return accountRepository.findByIdAndActiveTrue(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findByActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccountsByType(Account.AccountType type) {
        return accountRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAccountsByCurrency(String currency) {
        return accountRepository.findByCurrency(currency);
    }

    @Override
    public void updateBalance(Long accountId, double amount) {
        Account account = accountRepository.findByIdAndActiveTrue(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(BigDecimal.valueOf(amount));
        account.setBalance(newBalance);

        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAccountNameUnique(String name) {
        return !accountRepository.existsByNameAndActiveTrue(name);
    }
} 