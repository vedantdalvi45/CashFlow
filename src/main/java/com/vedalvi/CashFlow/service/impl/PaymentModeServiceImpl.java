package com.vedalvi.CashFlow.service.impl;


import com.vedalvi.CashFlow.dto.request.PaymentModeRequest;
import com.vedalvi.CashFlow.exception.DuplicateEntryException;
import com.vedalvi.CashFlow.exception.ResourceNotFoundException;
import com.vedalvi.CashFlow.model.PaymentMode;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.PaymentModeRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.PaymentModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentModeServiceImpl implements PaymentModeService {

    private final PaymentModeRepository paymentModeRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentMode createPaymentMode(PaymentMode paymentModeDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        if (paymentModeRepository.findByModeNameAndUserEmail(paymentModeDto.getModeName(), userEmail).isPresent()) {
            throw new DuplicateEntryException("Payment mode with name '" + paymentModeDto.getModeName() + "' already exists for this user.");
        }


        PaymentMode paymentMode = PaymentMode.builder()
                .modeName(paymentModeDto.getModeName())
                .paymentType(paymentModeDto.getPaymentType())
                .balance(new BigDecimal(0))
                .user(user) // Associate with the current user
                .build();

        return paymentModeRepository.save(paymentMode);
    }

    @Override
    public List<PaymentMode> getPaymentModesForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));
        if (user != null) {
            return paymentModeRepository.findByUserIdOrUserIdIsNull(user.getId());
        }
        else
            throw new AccessDeniedException("Access Denied");

    }

    @Override
    public PaymentMode updatePaymentMode(Long paymentModeId, PaymentMode paymentModeDetails, String userEmail) {
        return null;
    }

    @Override
    public void deletePaymentMode(Long paymentModeId, String userEmail) {

    }


}
