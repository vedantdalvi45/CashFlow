package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.exception.ResourceNotFoundException;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.TransactionRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public List<Transaction> getTransactionsForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
        return transactionRepository.findByUserIdOrderByTimeDesc(user.getId());
    }

    // Add logic for creating, updating, and deleting transactions
}
