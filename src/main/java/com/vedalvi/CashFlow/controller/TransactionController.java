package com.vedalvi.CashFlow.controller;


import com.vedalvi.CashFlow.dto.response.TransactionResponse;
import com.vedalvi.CashFlow.dto.request.TransactionRequest;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.security.CustomUserDetails;
import com.vedalvi.CashFlow.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    // Use the interface, not the implementation, for better decoupling.
    // @RequiredArgsConstructor handles the injection, so @Autowired is not needed.
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Transaction> transactions = transactionService.getTransactionsForUser(currentUser.getUsername());

        // Map the list of Transaction entities to a list of TransactionResponse DTOs
        List<TransactionResponse> transactionResponses = transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .id(transaction.getId())
                        .amount(transaction.getAmount())
                        .transactionType(transaction.getType().name())
                        .time(transaction.getTime())
                        .description(transaction.getDescription())
                        .categoryName(transaction.getCategory().getName())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionResponses);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody TransactionRequest transaction,
            @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Transaction createdTransaction = transactionService.createTransaction(transaction, customUserDetails.getUsername());
        return ResponseEntity.ok(createdTransaction);

    }







}
