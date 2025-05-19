package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.TradeTransaction;  
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeTransactionRepository extends CrudRepository&lt;TradeTransaction, Long&gt; {
}