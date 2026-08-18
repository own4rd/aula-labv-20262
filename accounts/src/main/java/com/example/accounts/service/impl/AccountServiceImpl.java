package com.example.accounts.service.impl;

import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UUID create(Account account) {
        return accountRepository.save(account).getId();
    }

    @Override
    public Account update(Account account, UUID uuid) {
        Account existing = findById(uuid);
        existing.setEmail(account.getEmail());
        existing.setPassword(account.getPassword());
        return accountRepository.save(existing);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(UUID uuid) {
        return accountRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + uuid));
    }

    @Override
    public void delete(UUID uuid) {
        if (!accountRepository.existsById(uuid)) {
            throw new EntityNotFoundException("Account not found: " + uuid);
        }
        accountRepository.deleteById(uuid);
    }
}
