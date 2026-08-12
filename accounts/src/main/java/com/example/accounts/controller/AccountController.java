package com.example.accounts.controller;

import com.example.accounts.model.Account;
import com.example.accounts.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;


// http://localhost:8080/accounts/hello

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Account account) {
        UUID uuid = accountService.create(account);
        URI location = URI.create(
                "/customers/" + uuid);

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Account>> list() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok().body(accounts);
    }
}
