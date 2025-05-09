package com.example.gemfirestarter.controller;

import com.example.gemfirestarter.model.Portfolio;
import com.example.gemfirestarter.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{userId}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String userId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolio(userId);
            return ResponseEntity.ok(portfolio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Portfolio> createPortfolio(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0.00") BigDecimal initialBalance) {
        Portfolio portfolio = portfolioService.createPortfolio(userId, initialBalance);
        return new ResponseEntity<>(portfolio, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/value")
    public ResponseEntity<BigDecimal> getPortfolioValue(@PathVariable String userId) {
        try {
            Portfolio portfolio = portfolioService.getPortfolio(userId);
            // Recalculate the portfolio value to ensure it's up-to-date
            portfolioService.calculatePortfolioValue(portfolio);
            return ResponseEntity.ok(portfolio.getTotalValue());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top")
    public ResponseEntity<List<Portfolio>> getTopPortfolios() {
        List<Portfolio> topPortfolios = portfolioService.getTopPortfolios();
        return ResponseEntity.ok(topPortfolios);
    }

    @GetMapping("/by-stock/{stockSymbol}")
    public ResponseEntity<List<Portfolio>> getPortfoliosByStockHolding(@PathVariable String stockSymbol) {
        List<Portfolio> portfolios = portfolioService.getPortfoliosByStockHolding(stockSymbol);
        return ResponseEntity.ok(portfolios);
    }
}
