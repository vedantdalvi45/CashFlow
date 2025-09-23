package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.exception.ResourceNotFoundException;
import com.vedalvi.CashFlow.exception.TransactionNotFoundException;
import com.vedalvi.CashFlow.exception.UserNotFoundException;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.TransactionRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final UserRepository userRepository;


    @Override
    public Transaction createTransaction(Transaction transaction) {
        User user = userRepository.findById(transaction.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(transaction.getUser().getId()));
        transaction.setUser(user);
        return transactionRepository.save(transaction);
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
    public List<Transaction> getTransactionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<Transaction> transactions = transactionRepository.findByUser(user);

        return transactions;
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
        return transactionRepository.getTransactionsByType(type);
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
