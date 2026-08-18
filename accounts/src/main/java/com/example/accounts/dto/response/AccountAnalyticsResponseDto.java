package com.example.accounts.dto.response;

public record AccountAnalyticsResponseDto(long totalAccounts, long activeAccounts, double averageBalance) {
}
