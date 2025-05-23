package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for Stock entities
 * Uses Spring Data Redis for data access
 */
@Repository
public interface StockRepository extends CrudRepository<Stock, String> {
    
    /**
     * Find stocks by sector
     */
    List<Stock> findBySector(String sector);
    
    /**
     * Find stocks where current price is greater than specified amount
     */
    List<Stock> findByCurrentPriceGreaterThan(BigDecimal price);
    
    /**
     * Find stocks where current price is less than specified amount
     */
    List<Stock> findByCurrentPriceLessThan(BigDecimal price);
    
    /**
     * Find stocks by sector and price range
     */
    List<Stock> findBySectorAndCurrentPriceBetween(
            String sector, BigDecimal minPrice, BigDecimal maxPrice);
}