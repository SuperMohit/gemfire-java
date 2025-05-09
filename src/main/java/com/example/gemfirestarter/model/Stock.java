package com.example.gemfirestarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Region("stocks")
public class Stock implements Serializable {

    @Id
    private String symbol;

    @NotBlank
    private String companyName;

    @NotNull
    @Positive
    private BigDecimal currentPrice;

    @Positive
    private BigDecimal dailyHigh;

    @Positive
    private BigDecimal dailyLow;

    @Positive
    private Long volume;

    @Positive
    private BigDecimal marketCap;

    @NotNull
    private LocalDateTime lastUpdated;
}
