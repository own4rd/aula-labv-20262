package com.example.accounts.service;

import com.example.accounts.dto.request.LoginRequestDto;
import com.example.accounts.dto.request.RegisterRequestDto;

public interface AuthService {
    public String login(LoginRequestDto request);
    public String register(RegisterRequestDto request);
}
