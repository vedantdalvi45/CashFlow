package com.vedalvi.CashFlow.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(Long id) {
        super("Transaction not found with id: " + id);
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
