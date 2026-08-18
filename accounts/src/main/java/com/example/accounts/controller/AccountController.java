package com.example.accounts.controller;

import com.example.accounts.dto.request.CreateAccountRequestDto;
import com.example.accounts.dto.request.UpdateAccountRequestDto;
import com.example.accounts.dto.response.AccountAnalyticsResponseDto;
import com.example.accounts.dto.response.AccountResponseDto;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.model.Account;
import com.example.accounts.service.AccountAnalyticsService;
import com.example.accounts.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountAnalyticsService accountAnalyticsService;

    public AccountController(AccountService accountService, AccountMapper accountMapper, AccountAnalyticsService accountAnalyticsService) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.accountAnalyticsService = accountAnalyticsService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateAccountRequestDto accountDto) {
        Account account = accountMapper.fromCreateAccountRequestDtoToEntity(accountDto);
        UUID uuid = accountService.create(account);
        URI location = URI.create(
                "/accounts/" + uuid);

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> list() {
        List<Account> accounts = accountService.findAll();
        List<AccountResponseDto> accountResponseDtos = accountMapper.fromAccountToDto(accounts);
        return ResponseEntity.ok().body(accountResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> findById(@PathVariable UUID id) {
        Account account = accountService.findById(id);
        return ResponseEntity.ok().body(accountMapper.fromAccountToDto(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UpdateAccountRequestDto accountDto) {
        Account account = accountMapper.fromUpdateAccountRequestDtoToEntity(accountDto);
        Account updated = accountService.update(account, id);
        return ResponseEntity.ok().body(accountMapper.fromAccountToDto(updated));
    }

    @GetMapping("/analytics")
    public CompletableFuture<ResponseEntity<AccountAnalyticsResponseDto>> analytics() {
        return accountAnalyticsService.calculate()
                .thenApplyAsync(result ->
                        ResponseEntity.ok().body(
                                new AccountAnalyticsResponseDto(result.totalAccounts(), result.activeAccounts(), result.averageBalance())));
    }

    @GetMapping("/analytics/sync")
    public ResponseEntity<AccountAnalyticsResponseDto> analyticsSync() {
        AccountAnalyticsService.AccountAnalytics result = accountAnalyticsService.calculateSync();
        return ResponseEntity.ok().body(
                new AccountAnalyticsResponseDto(result.totalAccounts(), result.activeAccounts(), result.averageBalance()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
