package com.vedalvi.CashFlow.controller;


import com.vedalvi.CashFlow.dto.request.PaymentModeRequest;
import com.vedalvi.CashFlow.dto.response.PaymentModeResponse;
import com.vedalvi.CashFlow.model.PaymentMode;
import com.vedalvi.CashFlow.security.CustomUserDetails;
import com.vedalvi.CashFlow.service.impl.PaymentModeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSInput;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/payment-modes")
public class PaymentModeController {

    @Autowired
    private PaymentModeServiceImpl paymentModeService;

    @PostMapping
    public ResponseEntity<PaymentModeResponse> createPaymentMode(@RequestBody PaymentModeRequest paymentModeRequest,
                                                                 @AuthenticationPrincipal CustomUserDetails customUserDetails){
        PaymentMode paymentMode = PaymentMode.builder()
                .modeName(paymentModeRequest.getModeName())
                .paymentType(paymentModeRequest.getPaymentType())
                .build();
        PaymentMode paymentMode1 = paymentModeService.createPaymentMode(paymentMode, customUserDetails.getUsername());
        PaymentModeResponse paymentModeResponse = PaymentModeResponse.builder()
                .id(paymentMode1.getId())
                .modeName(paymentMode1.getModeName())
                .paymentType(paymentMode1.getPaymentType().toString())
                .balance(paymentMode1.getBalance())
                .build();

        return ResponseEntity.ok(paymentModeResponse);
    }

    @GetMapping
    public ResponseEntity<List<PaymentModeResponse>> getPaymentModesForUser(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        List<PaymentMode> paymentModes = paymentModeService.getPaymentModesForUser(customUserDetails.getUsername());

        List<PaymentModeResponse> paymentModeResponseList = paymentModes
                .stream()
                .sorted(Comparator.comparing(PaymentMode::getId))
                .map(paymentMode -> PaymentModeResponse.builder()
                        .id(paymentMode.getId())
                        .modeName(paymentMode.getModeName())
                        .paymentType(paymentMode.getPaymentType().toString())
                        .balance(paymentMode.getBalance())
                        .build())
                .toList();

        return ResponseEntity.ok(paymentModeResponseList);
    }
}
