package com.example.accounts.service.impl;

import com.example.accounts.dto.request.LoginRequestDto;
import com.example.accounts.dto.request.RegisterRequestDto;
import com.example.accounts.model.Account;
import com.example.accounts.repository.AccountRepository;
import com.example.accounts.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Account account = accountRepository
                .findByEmail(request.email())
                .orElseThrow();

        return jwtService.generateToken(account);
    }

    public String register(RegisterRequestDto request) {

        Account account = new Account();

        account.setEmail(request.email());
        account.setPassword(
                passwordEncoder.encode(request.password())
        );

        accountRepository.save(account);

        return jwtService.generateToken(account);
    }
}
