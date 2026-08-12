package com.example.accounts.service.impl;

import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.service.AccountService;
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
    public void update(Account account, UUID uuid) {

    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
