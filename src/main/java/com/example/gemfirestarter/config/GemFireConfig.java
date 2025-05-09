package com.example.gemfirestarter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableIndexing;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import com.example.gemfirestarter.model.TradeTransaction;
import com.example.gemfirestarter.model.Stock;
import com.example.gemfirestarter.model.Portfolio;

@Configuration
@EnableIndexing
@EnableEntityDefinedRegions(basePackageClasses = {TradeTransaction.class, Stock.class, Portfolio.class})
@EnableClusterConfiguration(useHttp = false)
@ClientCacheApplication(locators = @ClientCacheApplication.Locator(host = "0.0.0.0", port = 10334))
@EnableGemfireRepositories(basePackages = "com.example.gemfirestarter.repository")
public class GemFireConfig {
    // Configuration for connecting to external GemFire cluster
}

