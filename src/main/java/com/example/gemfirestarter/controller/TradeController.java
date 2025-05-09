package com.example.gemfirestarter.controller;

import com.example.gemfirestarter.model.TradeTransaction;
import com.example.gemfirestarter.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<TradeTransaction> createTradeOrder(@Valid @RequestBody TradeTransaction tradeRequest) {
        TradeTransaction createdTrade = tradeService.createTradeOrder(tradeRequest);
        return new ResponseEntity<>(createdTrade, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TradeTransaction>> getTradeHistory(@RequestParam String userId) {
        List<TradeTransaction> trades = tradeService.getTradeHistory(userId);
        return ResponseEntity.ok(trades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeTransaction> getTradeById(@PathVariable String id) {
        try {
            TradeTransaction trade = tradeService.executeTradeOrder(id);
            return ResponseEntity.ok(trade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<TradeTransaction> executeTradeOrder(@PathVariable String id) {
        try {
            TradeTransaction executedTrade = tradeService.executeTradeOrder(id);
            return ResponseEntity.ok(executedTrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<TradeTransaction> cancelTradeOrder(@PathVariable String id) {
        try {
            TradeTransaction canceledTrade = tradeService.cancelTradeOrder(id);
            return ResponseEntity.ok(canceledTrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TradeTransaction>> getPendingTradesByStockSymbol(@RequestParam String stockSymbol) {
        List<TradeTransaction> pendingTrades = tradeService.getPendingTradesByStockSymbol(stockSymbol);
        return ResponseEntity.ok(pendingTrades);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TradeTransaction>> getTradesByDateRange(
            @RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        List<TradeTransaction> trades = tradeService.getTradesByUserIdAndDateRange(userId, startDateTime, endDateTime);
        return ResponseEntity.ok(trades);
    }
}
