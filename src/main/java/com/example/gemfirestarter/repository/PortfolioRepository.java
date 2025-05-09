package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.Portfolio;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PortfolioRepository extends GemfireRepository<Portfolio, String> {

    @Query("SELECT * FROM /portfolios p WHERE p.totalValue >= $1")
    List<Portfolio> findByTotalValueGreaterThanEqual(BigDecimal minValue);
    
    @Query("SELECT * FROM /portfolios p ORDER BY p.totalValue DESC LIMIT 10")
    List<Portfolio> findTop10ByOrderByTotalValueDesc();
    
    @Query("SELECT * FROM /portfolios p WHERE p.holdings.stockSymbol = $1")
    List<Portfolio> findByHoldingsStockSymbol(String stockSymbol);
}
