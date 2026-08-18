package com.example.accounts.service;

import com.example.accounts.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    UUID create(Account account);
    Account update(Account account, UUID uuid);
    List<Account> findAll();
    Account findById(UUID uuid);
    void delete(UUID uuid);

}
