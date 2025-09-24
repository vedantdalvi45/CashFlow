package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.dto.request.PaymentModeRequest;
import com.vedalvi.CashFlow.dto.request.TransactionRequest;
import com.vedalvi.CashFlow.exception.InsufficientBalance;
import com.vedalvi.CashFlow.exception.NotFoundException;
import com.vedalvi.CashFlow.model.Category;
import com.vedalvi.CashFlow.model.PaymentMode;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.CategoryRepository;
import com.vedalvi.CashFlow.repository.PaymentModeRepository;
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

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final PaymentModeRepository paymentModeRepository;



    @Override
    public List<Transaction> getTransactionsForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<Transaction> transactions = transactionRepository.findByUser(user);
        if (transactions != null) {
            return transactions;
        }
        else
            throw new NotFoundException("Transactions not found");
    }

    @Override
    public Transaction getTransactionById(Long transactionId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return transactionRepository.findByIdAndUserId(transactionId, user.getId())
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
    }

    @Override
    public Transaction createTransaction(TransactionRequest transaction, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Transaction transaction1 = validateTransaction(transaction, user);

        return transactionRepository.save(transaction1);

    }

    private Transaction validateTransaction(TransactionRequest requestDto, User user){
        Transaction transaction = Transaction.builder()
                .type(requestDto.getType())
                .description(requestDto.getDescription())
                .amount(requestDto.getAmount())
                .time(requestDto.getTime())
                .user(user)
                .build();

        switch (requestDto.getType().toString()){
            case "INCOME":
                transaction.setCategory(getCategoryById(requestDto.getCategoryId()));
                PaymentMode toPaymentMode = getPaymentModeById(requestDto.getToPaymentModeId());
                toPaymentMode.setBalance(toPaymentMode.getBalance().add(requestDto.getAmount()));
                transaction.setToPaymentMode(toPaymentMode);
                paymentModeRepository.save(toPaymentMode);
                break;
            case "EXPENSE":
                transaction.setCategory(getCategoryById(requestDto.getCategoryId()));
                PaymentMode fromPaymentMode = getPaymentModeById(requestDto.getFromPaymentModeId());

                if (fromPaymentMode.getBalance().compareTo(requestDto.getAmount()) < 0)
                    throw new InsufficientBalance("Insufficient balance.");

                transaction.setFromPaymentMode(fromPaymentMode);
                fromPaymentMode.setBalance(fromPaymentMode.getBalance().subtract(requestDto.getAmount()));
                paymentModeRepository.save(transaction.getFromPaymentMode());

                break;
            case "TRANSFER":
                PaymentMode fromPaymentMode1 = getPaymentModeById(requestDto.getFromPaymentModeId());
                PaymentMode toPaymentMode1 = getPaymentModeById(requestDto.getToPaymentModeId());
                if (fromPaymentMode1.getBalance().compareTo(requestDto.getAmount()) < 0)
                    throw new InsufficientBalance("Insufficient balance.");

                fromPaymentMode1.setBalance(fromPaymentMode1.getBalance().subtract(requestDto.getAmount()));
                toPaymentMode1.setBalance(toPaymentMode1.getBalance().add(requestDto.getAmount()));

                transaction.setFromPaymentMode(fromPaymentMode1);
                transaction.setToPaymentMode(toPaymentMode1);

                paymentModeRepository.save(transaction.getFromPaymentMode());
                paymentModeRepository.save(transaction.getToPaymentMode());

                break;
            default:
                throw new NotFoundException("Invalid transaction type: " + requestDto.getType());
        }
        return transaction;
    }

    private PaymentMode getPaymentModeById(Long paymentModeId){
        if (paymentModeId == 0 || paymentModeId == null)
            return null;
        return paymentModeRepository.findById(paymentModeId)
                .orElseThrow(() -> new NotFoundException("Payment mode not found"));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }



    @Override
    public Long getTransactionIdByIdAndEmail(Long transactionId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, user.getId())
                .orElseThrow(()-> new NotFoundException("Transaction not found"));

        if (transaction != null) {
            return transaction.getId();
        } else {
            throw new NotFoundException("Transaction not found");
        }
    }


    @Override
    public Transaction updateTransaction(Long transactionId, Transaction requestDto, String userEmail) {
        return null;
    }


    @Override
    public void deleteTransaction(Long transactionId, String userEmail) {

    }
}
