package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.TradeTransaction;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeTransactionRepository extends GemfireRepository<TradeTransaction, String> {

    List<TradeTransaction> findByUserId(String userId);
    
    List<TradeTransaction> findByStockSymbol(String stockSymbol);
    
    List<TradeTransaction> findByUserIdAndStockSymbol(String userId, String stockSymbol);
    
    List<TradeTransaction> findByStatus(TradeTransaction.TradeStatus status);
    
    @Query("SELECT * FROM /trades t WHERE t.userId = $1 AND t.timestamp >= $2 AND t.timestamp <= $3")
    List<TradeTransaction> findByUserIdAndTimestampBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT * FROM /trades t WHERE t.stockSymbol = $1 AND t.status = 'PENDING'")
    List<TradeTransaction> findPendingTradesByStockSymbol(String stockSymbol);
}
