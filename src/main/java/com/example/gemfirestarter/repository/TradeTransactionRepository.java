package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.TradeTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for TradeTransaction entities
 * Uses Spring Data Redis for data access
 */
@Repository
public interface TradeTransactionRepository extends CrudRepository<TradeTransaction, String> {
    
    /**
     * Find trades by portfolio ID
     */
    List<TradeTransaction> findByPortfolioId(String portfolioId);
    
    /**
     * Find trades by symbol
     */
    List<TradeTransaction> findBySymbol(String symbol);
    
    /**
     * Find trades by type (BUY or SELL)
     */
    List<TradeTransaction> findByType(String type);
    
    /**
     * Find trades by portfolio ID and type
     */
    List<TradeTransaction> findByPortfolioIdAndType(String portfolioId, String type);
    
    /**
     * Find trades by timestamp range
     */
    List<TradeTransaction> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * Find trades by portfolio ID and timestamp range
     */
    List<TradeTransaction> findByPortfolioIdAndTimestampBetween(
            String portfolioId, LocalDateTime startTime, LocalDateTime endTime);
}