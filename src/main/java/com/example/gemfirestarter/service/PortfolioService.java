package com.example.gemfirestarter.service;

import com.example.gemfirestarter.model.Portfolio;
import com.example.gemfirestarter.model.Stock;
import com.example.gemfirestarter.model.TradeTransaction;
import com.example.gemfirestarter.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final StockService stockService;

    public Portfolio getPortfolio(String userId) {
        return portfolioRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found for user: " + userId));
    }
    
    public Optional<Portfolio> findPortfolio(String userId) {
        return portfolioRepository.findById(userId);
    }
    
    public Portfolio createPortfolio(String userId, BigDecimal initialCashBalance) {
        Portfolio portfolio = Portfolio.builder()
                .userId(userId)
                .holdings(new ArrayList<>())
                .totalValue(initialCashBalance)
                .cashBalance(initialCashBalance)
                .build();
        
        return portfolioRepository.save(portfolio);
    }
    
    public Portfolio updatePortfolio(TradeTransaction trade) {
        // Get the user's portfolio
        Portfolio portfolio = findPortfolio(trade.getUserId())
                .orElseGet(() -> createPortfolio(trade.getUserId(), BigDecimal.ZERO));
        
        // Get the current stock price
        Stock stock = stockService.getStockBySymbol(trade.getStockSymbol());
        
        // Calculate the trade amount
        BigDecimal tradeAmount = trade.getTotalAmount();
        
        // Update portfolio based on trade type
        if (trade.getTradeType() == TradeTransaction.TradeType.BUY) {
            // Check if user has enough cash
            if (portfolio.getCashBalance().compareTo(tradeAmount) < 0) {
                throw new IllegalStateException("Insufficient funds to execute trade");
            }
            
            // Deduct cash
            portfolio.setCashBalance(portfolio.getCashBalance().subtract(tradeAmount));
            
            // Add or update holding
            updateHolding(portfolio, trade.getStockSymbol(), trade.getQuantity(), trade.getPricePerShare(), true);
        } else if (trade.getTradeType() == TradeTransaction.TradeType.SELL) {
            // Check if user has the stock and enough quantity
            boolean hasStock = false;
            for (Portfolio.Holding holding : portfolio.getHoldings()) {
                if (holding.getStockSymbol().equals(trade.getStockSymbol())) {
                    if (holding.getQuantity() < trade.getQuantity()) {
                        throw new IllegalStateException("Insufficient shares to execute sell order");
                    }
                    hasStock = true;
                    break;
                }
            }
            
            if (!hasStock) {
                throw new IllegalStateException("Stock not found in portfolio: " + trade.getStockSymbol());
            }
            
            // Add cash
            portfolio.setCashBalance(portfolio.getCashBalance().add(tradeAmount));
            
            // Update holding
            updateHolding(portfolio, trade.getStockSymbol(), trade.getQuantity(), trade.getPricePerShare(), false);
        }
        
        // Recalculate total portfolio value
        calculatePortfolioValue(portfolio);
        
        // Save and return updated portfolio
        return portfolioRepository.save(portfolio);
    }
    
    private void updateHolding(Portfolio portfolio, String symbol, int quantity, BigDecimal price, boolean isBuy) {
        List<Portfolio.Holding> holdings = portfolio.getHoldings();
        
        // Find existing holding
        Optional<Portfolio.Holding> existingHolding = holdings.stream()
                .filter(h -> h.getStockSymbol().equals(symbol))
                .findFirst();
        
        if (isBuy) {
            if (existingHolding.isPresent()) {
                // Update existing holding
                Portfolio.Holding holding = existingHolding.get();
                int newQuantity = holding.getQuantity() + quantity;
                
                // Calculate new average purchase price
                BigDecimal totalCost = holding.getAveragePurchasePrice()
                        .multiply(BigDecimal.valueOf(holding.getQuantity()))
                        .add(price.multiply(BigDecimal.valueOf(quantity)));
                
                BigDecimal newAvgPrice = totalCost.divide(BigDecimal.valueOf(newQuantity), 2, BigDecimal.ROUND_HALF_UP);
                
                holding.setQuantity(newQuantity);
                holding.setAveragePurchasePrice(newAvgPrice);
                
                // Update current value
                Stock stock = stockService.getStockBySymbol(symbol);
                holding.setCurrentValue(stock.getCurrentPrice().multiply(BigDecimal.valueOf(newQuantity)));
            } else {
                // Create new holding
                Stock stock = stockService.getStockBySymbol(symbol);
                Portfolio.Holding newHolding = Portfolio.Holding.builder()
                        .stockSymbol(symbol)
                        .quantity(quantity)
                        .averagePurchasePrice(price)
                        .currentValue(stock.getCurrentPrice().multiply(BigDecimal.valueOf(quantity)))
                        .build();
                
                holdings.add(newHolding);
            }
        } else {
            // Sell operation
            if (existingHolding.isPresent()) {
                Portfolio.Holding holding = existingHolding.get();
                int newQuantity = holding.getQuantity() - quantity;
                
                if (newQuantity > 0) {
                    // Update holding
                    holding.setQuantity(newQuantity);
                    
                    // Update current value
                    Stock stock = stockService.getStockBySymbol(symbol);
                    holding.setCurrentValue(stock.getCurrentPrice().multiply(BigDecimal.valueOf(newQuantity)));
                } else {
                    // Remove holding if quantity is 0
                    holdings.remove(holding);
                }
            }
        }
    }
    
    public void calculatePortfolioValue(Portfolio portfolio) {
        BigDecimal totalValue = portfolio.getCashBalance();
        
        // Add value of all holdings
        for (Portfolio.Holding holding : portfolio.getHoldings()) {
            Stock stock = stockService.getStockBySymbol(holding.getStockSymbol());
            BigDecimal holdingValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(holding.getQuantity()));
            holding.setCurrentValue(holdingValue);
            totalValue = totalValue.add(holdingValue);
        }
        
        portfolio.setTotalValue(totalValue);
    }
    
    public List<Portfolio> getTopPortfolios() {
        return portfolioRepository.findTop10ByOrderByTotalValueDesc();
    }
    
    public List<Portfolio> getPortfoliosByStockHolding(String stockSymbol) {
        return portfolioRepository.findByHoldingsStockSymbol(stockSymbol);
    }
}
