package com.example.accounts.controller;

import com.example.accounts.dto.request.LoginRequestDto;
import com.example.accounts.dto.request.RegisterRequestDto;
import com.example.accounts.dto.response.AuthResponseDto;
import com.example.accounts.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Registrar uma nova conta",
            description = """
                Cria uma nova conta no sistema utilizando os dados informados.
                Após o cadastro, um token JWT é retornado para autenticação
                nas rotas protegidas da API.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Conta criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de cadastro inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Já existe uma conta cadastrada com o e-mail informado",
                    content = @Content
            )
    })
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
