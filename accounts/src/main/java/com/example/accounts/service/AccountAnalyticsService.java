package com.example.accounts.service;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AccountAnalyticsService {

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    public AccountAnalytics calculateSync() {
        long totalAccounts = simulate("total accounts");
        long activeAccounts = simulate("active accounts");
        double averageBalance = simulate("average balance");
        return new AccountAnalytics(totalAccounts, activeAccounts, averageBalance);
    }

    public CompletableFuture<AccountAnalytics> calculate() {
        CompletableFuture<Long> totalAccounts = simulateRequest("total accounts");
        CompletableFuture<Long> activeAccounts = simulateRequest("active accounts");
        CompletableFuture<Double> averageBalance = simulateRequest("average balance");

        return CompletableFuture
                .allOf(totalAccounts, activeAccounts, averageBalance)
                .thenApplyAsync(ignored ->
                        new AccountAnalytics(totalAccounts.join(), activeAccounts.join(), averageBalance.join()),
                        executor);
    }

    @SuppressWarnings("unchecked")
    private <T> T simulate(String name) {
        try {
            // Ruído: 0,3 e 1,2 segundos (300 a 1199 ms)
            long delay = ThreadLocalRandom.current().nextLong(300, 1200);
            Thread.sleep(delay);
            System.out.println(Thread.currentThread().getName() + " finished: " + name + " after " + delay + "ms");
            return (T) mockResult(name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException(e);
        }
    }

    private <T> CompletableFuture<T> simulateRequest(String name) {
        return CompletableFuture.supplyAsync(() -> simulate(name), executor);
    }

    private Object mockResult(String name) {
        return switch (name) {
            case "total accounts" -> ThreadLocalRandom.current().nextLong(1000, 5000);
            case "active accounts" -> ThreadLocalRandom.current().nextLong(1000, 5000);
            default -> ThreadLocalRandom.current().nextDouble(1000, 10000);
        };
    }

    public record AccountAnalytics(long totalAccounts, long activeAccounts, double averageBalance) {
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
