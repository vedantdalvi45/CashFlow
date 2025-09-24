package com.vedalvi.CashFlow.repository;


import com.vedalvi.CashFlow.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {

    List<PaymentMode> findByUserIdOrUserIdIsNull(Long userId);

    PaymentMode findPaymentModeByModeName(String modeName);
    Optional<PaymentMode> findByModeNameAndUserEmail(String modeName, String userEmail);

}
