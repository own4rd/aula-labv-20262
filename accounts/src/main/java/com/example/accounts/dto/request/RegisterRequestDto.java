package com.example.accounts.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para criação de uma conta")
public record RegisterRequestDto(
        String name,

        @Schema(
                description = "E-mail da conta",
                example = "usuario@email.com"
        )
        String email,

        @Schema(
                description = "Senha da conta",
                example = "Senha@123"
        )
        String password
) {}
