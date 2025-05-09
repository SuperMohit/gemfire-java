package com.example.gemfirestarter.controller;

import com.example.gemfirestarter.model.Stock;
import com.example.gemfirestarter.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Stock> getStockBySymbol(@PathVariable String symbol) {
        try {
            Stock stock = stockService.getStockBySymbol(symbol);
            return ResponseEntity.ok(stock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) {
        Stock createdStock = stockService.createStock(stock);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    @PutMapping("/{symbol}/price")
    public ResponseEntity<Stock> updateStockPrice(
            @PathVariable String symbol,
            @RequestParam BigDecimal price) {
        try {
            Stock updatedStock = stockService.updateStockPrice(symbol, price);
            return ResponseEntity.ok(updatedStock);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Stock>> searchStocks(@RequestParam String query) {
        List<Stock> stocks = stockService.searchStocksByCompanyName(query);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Stock>> getStocksByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Stock> stocks = stockService.getStocksByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/top-volume")
    public ResponseEntity<List<Stock>> getTopVolumeStocks() {
        List<Stock> stocks = stockService.getTopVolumeStocks();
        return ResponseEntity.ok(stocks);
    }

    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> deleteStock(@PathVariable String symbol) {
        try {
            stockService.deleteStock(symbol);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
