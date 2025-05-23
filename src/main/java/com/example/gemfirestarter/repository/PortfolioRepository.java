package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for Portfolio entities
 * Uses Spring Data Redis for data access
 */
@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, String> {
    
    /**
     * Find portfolios by user ID
     */
    List<Portfolio> findByUserId(String userId);
    
    /**
     * Find portfolios with total value greater than specified amount
     */
    List<Portfolio> findByTotalValueGreaterThan(BigDecimal value);
    
    /**
     * Find portfolios with total value less than specified amount
     */
    List<Portfolio> findByTotalValueLessThan(BigDecimal value);
    
    /**
     * Find portfolios by user ID and with total value in specified range
     */
    List<Portfolio> findByUserIdAndTotalValueBetween(
            String userId, BigDecimal minValue, BigDecimal maxValue);
}