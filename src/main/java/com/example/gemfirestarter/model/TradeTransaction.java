package com.example.gemfirestarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor  
@RedisHash("trades")
public class TradeTransaction implements Serializable {

@Id
private Long id;
private String type;
private Double price;
private Long quantity;
private Long stockId;
}