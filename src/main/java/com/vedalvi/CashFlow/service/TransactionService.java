package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction getTransactionById(Long id);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByUserId(Long userId);

    Transaction updateTransaction(Long id, Transaction transaction);

    void deleteTransaction(Long id);

    List<Transaction> getTransactionsByType(String type);

    List<Transaction> getTransactionsBetweenDates(String startDate, String endDate);

    Double getTotalAmountByUserId(Long userId);

    boolean validateTransaction(Transaction transaction);

    Transaction processTransaction(Transaction transaction);

}
