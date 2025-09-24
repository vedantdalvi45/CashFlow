package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.dto.request.TransactionRequest;
import com.vedalvi.CashFlow.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactionsForUser(String userEmail);

    Transaction getTransactionById(Long transactionId, String userEmail);

    Transaction createTransaction(TransactionRequest requestDto, String userEmail);

    Transaction updateTransaction(Long transactionId, Transaction requestDto, String userEmail);

    void deleteTransaction(Long transactionId, String userEmail);

    Long getTransactionIdByIdAndEmail(Long transactionId, String userEmail);

}
