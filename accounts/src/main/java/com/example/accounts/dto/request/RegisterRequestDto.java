package com.example.accounts.dto.request;

public record RegisterRequestDto(
        String name,
        String email,
        String password
) {}
