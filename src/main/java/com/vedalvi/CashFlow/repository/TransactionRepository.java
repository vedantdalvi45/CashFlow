package com.vedalvi.CashFlow.repository;

import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByTimeDesc(Long userId);

    List<Transaction> findByUser(User user);

    List<Transaction> getTransactionsByType(String type);

    Optional<Transaction> findByIdAndUserId(Long transactionId, Long userId);





}
