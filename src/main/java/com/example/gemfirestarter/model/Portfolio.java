package com.example.gemfirestarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Region("portfolios")
public class Portfolio implements Serializable {

    @Id
    private String userId;

    @NotNull
    private List<Holding> holdings;

    @NotNull
    private BigDecimal totalValue;

    @NotNull
    private BigDecimal cashBalance;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Holding implements Serializable {
        @NotBlank
        private String stockSymbol;
        
        @NotNull
        private Integer quantity;
        
        @NotNull
        private BigDecimal averagePurchasePrice;
        
        @NotNull
        private BigDecimal currentValue;
    }
}
