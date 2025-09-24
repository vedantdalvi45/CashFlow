package com.vedalvi.CashFlow.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentModeResponse {
    private Long id;
    private String modeName;
    private String paymentType;
    private double balance;
}
