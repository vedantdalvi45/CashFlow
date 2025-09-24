package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.dto.request.PaymentModeRequest;
import com.vedalvi.CashFlow.model.PaymentMode;
import java.util.List;

public interface PaymentModeService {
    PaymentMode createPaymentMode(PaymentMode paymentModeDto, String userEmail);
    List<PaymentMode> getPaymentModesForUser(String userEmail);
    PaymentMode updatePaymentMode(Long paymentModeId, PaymentMode paymentModeDetails, String userEmail);
    void deletePaymentMode(Long paymentModeId, String userEmail);
}