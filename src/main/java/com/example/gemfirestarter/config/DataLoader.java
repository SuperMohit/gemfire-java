package com.example.gemfirestarter.config;

import com.example.gemfirestarter.model.Portfolio;
import com.example.gemfirestarter.model.Stock;
import com.example.gemfirestarter.model.TradeTransaction;
import com.example.gemfirestarter.service.PortfolioService;
import com.example.gemfirestarter.service.StockService;
import com.example.gemfirestarter.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final StockService stockService;
    private final PortfolioService portfolioService;
    private final TradeService tradeService;

    @Bean
    @Profile("!prod") // Only run in non-production environments
    public CommandLineRunner loadData() {
        return args -> {
            // Create sample stocks
            createSampleStocks();
            
            // Create sample portfolios
            createSamplePortfolios();
            
            // Create sample trades
            createSampleTrades();
        };
    }
    
    private void createSampleStocks() {
        // Create some sample stocks
        Stock apple = Stock.builder()
                .symbol("AAPL")
                .companyName("Apple Inc.")
                .currentPrice(new BigDecimal("175.50"))
                .dailyHigh(new BigDecimal("178.20"))
                .dailyLow(new BigDecimal("174.30"))
                .volume(15000000L)
                .marketCap(new BigDecimal("2850000000000"))
                .lastUpdated(LocalDateTime.now())
                .build();
        
        Stock microsoft = Stock.builder()
                .symbol("MSFT")
                .companyName("Microsoft Corporation")
                .currentPrice(new BigDecimal("325.75"))
                .dailyHigh(new BigDecimal("328.90"))
                .dailyLow(new BigDecimal("323.10"))
                .volume(12000000L)
                .marketCap(new BigDecimal("2450000000000"))
                .lastUpdated(LocalDateTime.now())
                .build();
        
        Stock amazon = Stock.builder()
                .symbol("AMZN")
                .companyName("Amazon.com Inc.")
                .currentPrice(new BigDecimal("135.20"))
                .dailyHigh(new BigDecimal("137.50"))
                .dailyLow(new BigDecimal("134.10"))
                .volume(10000000L)
                .marketCap(new BigDecimal("1380000000000"))
                .lastUpdated(LocalDateTime.now())
                .build();
        
        Stock google = Stock.builder()
                .symbol("GOOGL")
                .companyName("Alphabet Inc.")
                .currentPrice(new BigDecimal("142.30"))
                .dailyHigh(new BigDecimal("144.80"))
                .dailyLow(new BigDecimal("141.20"))
                .volume(8000000L)
                .marketCap(new BigDecimal("1850000000000"))
                .lastUpdated(LocalDateTime.now())
                .build();
        
        Stock tesla = Stock.builder()
                .symbol("TSLA")
                .companyName("Tesla, Inc.")
                .currentPrice(new BigDecimal("245.60"))
                .dailyHigh(new BigDecimal("250.30"))
                .dailyLow(new BigDecimal("242.10"))
                .volume(20000000L)
                .marketCap(new BigDecimal("780000000000"))
                .lastUpdated(LocalDateTime.now())
                .build();
        
        // Save stocks
        stockService.createStock(apple);
        stockService.createStock(microsoft);
        stockService.createStock(amazon);
        stockService.createStock(google);
        stockService.createStock(tesla);
    }
    
    private void createSamplePortfolios() {
        // Create a sample portfolio for user1
        portfolioService.createPortfolio("user1", new BigDecimal("10000.00"));
        
        // Create a sample portfolio for user2
        portfolioService.createPortfolio("user2", new BigDecimal("25000.00"));
    }
    
    private void createSampleTrades() {
        // Create a sample buy trade for user1
        TradeTransaction buyApple = TradeTransaction.builder()
                .id(UUID.randomUUID().toString())
                .userId("user1")
                .stockSymbol("AAPL")
                .quantity(10)
                .pricePerShare(new BigDecimal("175.50"))
                .totalAmount(new BigDecimal("1755.00"))
                .tradeType(TradeTransaction.TradeType.BUY)
                .orderType(TradeTransaction.OrderType.MARKET)
                .status(TradeTransaction.TradeStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .commissionFees(new BigDecimal("9.99"))
                .build();
        
        TradeTransaction buyMicrosoft = TradeTransaction.builder()
                .id(UUID.randomUUID().toString())
                .userId("user2")
                .stockSymbol("MSFT")
                .quantity(5)
                .pricePerShare(new BigDecimal("325.75"))
                .totalAmount(new BigDecimal("1628.75"))
                .tradeType(TradeTransaction.TradeType.BUY)
                .orderType(TradeTransaction.OrderType.MARKET)
                .status(TradeTransaction.TradeStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .commissionFees(new BigDecimal("9.99"))
                .build();
        
        // Save trades
        tradeService.createTradeOrder(buyApple);
        tradeService.createTradeOrder(buyMicrosoft);
        
        // Execute trades
        try {
            tradeService.executeTradeOrder(buyApple.getId());
            tradeService.executeTradeOrder(buyMicrosoft.getId());
        } catch (Exception e) {
            System.err.println("Error executing sample trades: " + e.getMessage());
        }
    }
}
