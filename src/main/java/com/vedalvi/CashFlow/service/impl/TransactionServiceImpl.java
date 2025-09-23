package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.exception.ResourceNotFoundException;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.TransactionRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final UserRepository userRepository;


    @Override
    public Transaction createTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
        return transactionRepository.findByUserIdOrderByTimeDesc(user.getId());
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        return null;
    }

    @Override
    public void deleteTransaction(Long id) {

    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
        return List.of();
    }

    @Override
    public List<Transaction> getTransactionsBetweenDates(String startDate, String endDate) {
        return List.of();
    }

    @Override
    public Double getTotalAmountByUserId(Long userId) {
        return 0.0;
    }

    @Override
    public boolean validateTransaction(Transaction transaction) {
        return false;
    }

    @Override
    public Transaction processTransaction(Transaction transaction) {
        return null;
    }
}
