package com.example.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// http://localhost:8080/accounts/hello

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping
    public String hello() {
        return "Hello";
    }
}
