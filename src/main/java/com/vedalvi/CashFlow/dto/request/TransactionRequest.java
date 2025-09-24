package com.vedalvi.CashFlow.dto.request;

import com.vedalvi.CashFlow.model.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
public class TransactionRequest {

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;

    @NotNull(message = "Transaction time cannot be null")
    private Instant time;

    // IDs of related entities
    private Long categoryId;
    private Long fromPaymentModeId;
    private Long toPaymentModeId;
    private Set<Long> tagIds;
}
