package com.example.accounts.controller;

import com.example.accounts.dto.request.CreateAccountRequestDto;
import com.example.accounts.dto.request.UpdateAccountRequestDto;
import com.example.accounts.dto.response.AccountResponseDto;
import com.example.accounts.mapper.AccountMapper;
import com.example.accounts.model.Account;
import com.example.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Tag(name = "Accounts", description = "Operações relacionadas às contas")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @Operation(summary = "Atualizar uma conta")
    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> list() {
        List<Account> accounts = accountService.findAll();
        List<AccountResponseDto> accountResponseDtos = accountMapper.fromAccountToDto(accounts);
        return ResponseEntity.ok().body(accountResponseDtos);
    }

    @Operation(summary = "Busca uma conta pelo ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Conta encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta não encontrada"
            )
    })
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
