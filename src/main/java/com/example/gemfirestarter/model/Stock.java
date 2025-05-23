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
@RedisHash("stocks")
public class Stock implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String symbol;
    
    private String companyName;
    
    private BigDecimal currentPrice;
    
    @Indexed
    private String sector;
    
    private LocalDateTime lastUpdated;
    
    private BigDecimal dailyHigh;
    
    private BigDecimal dailyLow;
    
    public Stock(String symbol, String companyName, BigDecimal currentPrice, String sector) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.sector = sector;
        this.lastUpdated = LocalDateTime.now();
        this.dailyHigh = currentPrice;
        this.dailyLow = currentPrice;
    }
}