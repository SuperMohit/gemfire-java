package com.example.gemfirestarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("trades")
public class TradeTransaction implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @Indexed
    private String symbol;
    
    private Integer quantity;
    
    private BigDecimal price;
    
    private LocalDateTime timestamp;
    
    private String type; // BUY or SELL
    
    @Indexed
    private String portfolioId;
    
    private BigDecimal totalValue;
    
    public TradeTransaction(String symbol, Integer quantity, BigDecimal price, String type, String portfolioId) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.portfolioId = portfolioId;
        this.timestamp = LocalDateTime.now();
        this.totalValue = price.multiply(BigDecimal.valueOf(quantity));
    }
}