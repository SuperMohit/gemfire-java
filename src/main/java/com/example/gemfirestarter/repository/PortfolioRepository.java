package com.example.gemfirestarter.repository;

import com.example.gemfirestarter.model.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends CrudRepository&lt;Portfolio, Long&gt; {

List&lt;Portfolio&gt; findByStocksSymbol(String symbol);

List&lt;Portfolio&gt; findByTradeTransactionsStockId(Long stockId);

}