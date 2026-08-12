package com.example.accounts.service;

import com.example.accounts.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    UUID create(Account account);
    void update(Account account, UUID uuid);
    List<Account> findAll();

}
