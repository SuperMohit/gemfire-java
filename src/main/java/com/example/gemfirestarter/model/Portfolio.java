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
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("portfolios")
public class Portfolio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    private String name;
    
    private Map<String, Integer> holdings = new HashMap<>();  // symbol -> quantity
    
    private BigDecimal cash;
    
    private BigDecimal totalValue;
    
    public Portfolio(String userId, String name, BigDecimal initialInvestment) {
        this.userId = userId;
        this.name = name;
        this.cash = initialInvestment;
        this.totalValue = initialInvestment;
    }
    
    public void updateHolding(String symbol, Integer quantityChange) {
        Integer currentQuantity = holdings.getOrDefault(symbol, 0);
        Integer newQuantity = currentQuantity + quantityChange;
        
        if (newQuantity <= 0) {
            holdings.remove(symbol);
        } else {
            holdings.put(symbol, newQuantity);
        }
    }
}