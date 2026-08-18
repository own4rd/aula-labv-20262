package com.example.accounts.mapper;

import com.example.accounts.dto.request.CreateAccountRequestDto;
import com.example.accounts.dto.request.UpdateAccountRequestDto;
import com.example.accounts.dto.response.AccountResponseDto;
import com.example.accounts.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {

    public Account fromCreateAccountRequestDtoToEntity(CreateAccountRequestDto accountDto) {
        return new Account(
                accountDto.email(),
                accountDto.password()
        );
    }

    public Account fromUpdateAccountRequestDtoToEntity(UpdateAccountRequestDto accountDto) {
        return new Account(
                accountDto.email(),
                accountDto.password()
        );
    }

    public AccountResponseDto fromAccountToDto(Account account) {
        return new AccountResponseDto(account.getId(), account.getEmail());
    }

    public List<AccountResponseDto> fromAccountToDto(List<Account> accounts) {
        return accounts.stream()
                .map(this::fromAccountToDto).toList();
    }
}
