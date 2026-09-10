package com.example.accounts.controller;

import com.example.accounts.dto.request.LoginRequestDto;
import com.example.accounts.dto.request.RegisterRequestDto;
import com.example.accounts.dto.response.AuthResponseDto;
import com.example.accounts.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody LoginRequestDto request
    ) {
        String token = authService.login(request);

        return ResponseEntity.ok(
                new AuthResponseDto(token)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        String token = authService.register(request);

        return ResponseEntity.ok(
                new AuthResponseDto(token)
        );
    }
}
