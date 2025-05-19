package com.example.gemfirestarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("portfolios")
public class Portfolio implements Serializable {

@Id
private Long id;
private Double purchasePrice;
private List&lt;Stock&gt; stocks;
private TradeTransaction tradeTransactions;
}