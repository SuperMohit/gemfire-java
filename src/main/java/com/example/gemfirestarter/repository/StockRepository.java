package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.Stock;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StockRepository extends GemfireRepository<Stock, String> {

    @Query("SELECT * FROM /stocks s WHERE s.companyName LIKE $1")
    List<Stock> findByCompanyNameLike(String companyNamePattern);
    
    @Query("SELECT * FROM /stocks s WHERE s.currentPrice <= $1")
    List<Stock> findByCurrentPriceLessThanEqual(BigDecimal maxPrice);
    
    @Query("SELECT * FROM /stocks s WHERE s.currentPrice >= $1")
    List<Stock> findByCurrentPriceGreaterThanEqual(BigDecimal minPrice);
    
    @Query("SELECT * FROM /stocks s WHERE s.currentPrice >= $1 AND s.currentPrice <= $2")
    List<Stock> findByCurrentPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    @Query("SELECT * FROM /stocks s ORDER BY s.volume DESC LIMIT 10")
    List<Stock> findTop10ByOrderByVolumeDesc();
}
