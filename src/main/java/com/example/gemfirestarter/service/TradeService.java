package com.example.gemfirestarter.service;

import com.example.gemfirestarter.model.TradeTransaction;
import com.example.gemfirestarter.repository.TradeTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeTransactionRepository tradeRepository;
    private final PortfolioService portfolioService;

    public TradeTransaction createTradeOrder(TradeTransaction tradeRequest) {
        // Set generated ID if not provided
        if (tradeRequest.getId() == null || tradeRequest.getId().isEmpty()) {
            tradeRequest.setId(UUID.randomUUID().toString());
        }
        
        // Set timestamp to now if not provided
        if (tradeRequest.getTimestamp() == null) {
            tradeRequest.setTimestamp(LocalDateTime.now());
        }
        
        // Set initial status to PENDING
        tradeRequest.setStatus(TradeTransaction.TradeStatus.PENDING);
        
        // Save the trade order
        return tradeRepository.save(tradeRequest);
    }
    
    public TradeTransaction executeTradeOrder(String transactionId) {
        TradeTransaction trade = tradeRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + transactionId));
        
        // Only execute if the trade is in PENDING status
        if (trade.getStatus() != TradeTransaction.TradeStatus.PENDING) {
            throw new IllegalStateException("Trade is not in PENDING status: " + trade.getStatus());
        }
        
        try {
            // Update portfolio based on the trade
            portfolioService.updatePortfolio(trade);
            
            // Mark trade as completed
            trade.setStatus(TradeTransaction.TradeStatus.COMPLETED);
            return tradeRepository.save(trade);
        } catch (Exception e) {
            // Mark trade as failed if an error occurs
            trade.setStatus(TradeTransaction.TradeStatus.FAILED);
            tradeRepository.save(trade);
            throw e;
        }
    }
    
    public List<TradeTransaction> getTradeHistory(String userId) {
        return tradeRepository.findByUserId(userId);
    }
    
    public TradeTransaction cancelTradeOrder(String transactionId) {
        TradeTransaction trade = tradeRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found with ID: " + transactionId));
        
        // Only cancel if the trade is in PENDING status
        if (trade.getStatus() != TradeTransaction.TradeStatus.PENDING) {
            throw new IllegalStateException("Trade cannot be canceled. Current status: " + trade.getStatus());
        }
        
        trade.setStatus(TradeTransaction.TradeStatus.CANCELED);
        return tradeRepository.save(trade);
    }
    
    public List<TradeTransaction> getPendingTradesByStockSymbol(String stockSymbol) {
        return tradeRepository.findPendingTradesByStockSymbol(stockSymbol);
    }
    
    public List<TradeTransaction> getTradesByUserIdAndDateRange(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return tradeRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate);
    }
}
