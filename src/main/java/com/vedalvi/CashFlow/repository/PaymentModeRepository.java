package com.vedalvi.CashFlow.repository;


import com.vedalvi.CashFlow.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentModeRepository extends JpaRepository<PaymentMode, Long> {

    List<PaymentMode> findByUserIdOrUserIdIsNull(Long userId);
}
