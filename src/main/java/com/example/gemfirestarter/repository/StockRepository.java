package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository&lt;Stock, Long&gt; {
}