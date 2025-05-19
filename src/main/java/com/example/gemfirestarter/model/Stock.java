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
@RedisHash("stocks")
public class Stock implements Serializable {

@Id
private Long id;
private String symbol;
private Double price;
private Long quantity;
}