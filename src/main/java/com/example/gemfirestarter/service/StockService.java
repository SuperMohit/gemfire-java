package com.example.gemfirestarter.service;

import com.example.gemfirestarter.model.Stock;
import com.example.gemfirestarter.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findById(symbol)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with symbol: " + symbol));
    }
    
    public Optional<Stock> findStockBySymbol(String symbol) {
        return stockRepository.findById(symbol);
    }
    
    public List<Stock> getAllStocks() {
        return (List<Stock>) stockRepository.findAll();
    }
    
    public Stock createStock(Stock stock) {
        // Set last updated timestamp if not provided
        if (stock.getLastUpdated() == null) {
            stock.setLastUpdated(LocalDateTime.now());
        }
        
        return stockRepository.save(stock);
    }
    
    public Stock updateStockPrice(String symbol, BigDecimal newPrice) {
        Stock stock = getStockBySymbol(symbol);
        
        // Update daily high/low if needed
        if (stock.getDailyHigh() == null || newPrice.compareTo(stock.getDailyHigh()) > 0) {
            stock.setDailyHigh(newPrice);
        }
        
        if (stock.getDailyLow() == null || newPrice.compareTo(stock.getDailyLow()) < 0) {
            stock.setDailyLow(newPrice);
        }
        
        stock.setCurrentPrice(newPrice);
        stock.setLastUpdated(LocalDateTime.now());
        
        return stockRepository.save(stock);
    }
    
    public List<Stock> searchStocksByCompanyName(String searchTerm) {
        return stockRepository.findByCompanyNameLike("%" + searchTerm + "%");
    }
    
    public List<Stock> getStocksByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return stockRepository.findByCurrentPriceBetween(minPrice, maxPrice);
    }
    
    public List<Stock> getTopVolumeStocks() {
        return stockRepository.findTop10ByOrderByVolumeDesc();
    }
    
    public void deleteStock(String symbol) {
        stockRepository.deleteById(symbol);
    }
}
