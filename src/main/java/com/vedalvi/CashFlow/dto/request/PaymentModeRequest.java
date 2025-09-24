package com.vedalvi.CashFlow.dto.request;

import com.vedalvi.CashFlow.model.enums.PaymentPlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentModeRequest {

    @NotBlank(message = "Mode name cannot be blank")
    @Size(min = 2, max = 100, message = "Mode name must be between 2 and 100 characters")
    private String modeName;

    @NotNull(message = "Payment type cannot be null")
    private PaymentPlatform paymentType;


}
