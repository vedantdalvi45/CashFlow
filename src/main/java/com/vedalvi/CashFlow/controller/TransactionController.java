package com.vedalvi.CashFlow.controller;


import com.vedalvi.CashFlow.dto.request.TransactionRequest;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.enums.TransactionType;
import com.vedalvi.CashFlow.repository.CategoryRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.security.CustomUserDetails;
import com.vedalvi.CashFlow.service.impl.TransactionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionServiceImpl transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Transaction>> getUserTransactions(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Transaction> transactions = transactionService.getTransactionsForUser(currentUser.getUsername());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody TransactionRequest transaction,
            @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Transaction createdTransaction = transactionService.createTransaction(transaction, customUserDetails.getUsername());
        return ResponseEntity.ok(createdTransaction);

    }







}
