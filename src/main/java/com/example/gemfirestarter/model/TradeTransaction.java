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
@Region("trades")
public class TradeTransaction implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String userId;

    @NotBlank
    private String stockSymbol;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private BigDecimal pricePerShare;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private TradeType tradeType;

    @NotNull
    private OrderType orderType;

    @NotNull
    private TradeStatus status;

    @NotNull
    private LocalDateTime timestamp;

    private BigDecimal commissionFees;

    public enum TradeType {
        BUY, SELL
    }

    public enum OrderType {
        MARKET, LIMIT, STOP
    }

    public enum TradeStatus {
        PENDING, COMPLETED, FAILED, CANCELED
    }
}
