package com.vedalvi.CashFlow.repository;

import com.vedalvi.CashFlow.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByTimeDesc(Long userId);
}
