package com.vedalvi.CashFlow.exception;


public class InsufficientBalance extends RuntimeException{
    public InsufficientBalance(String message) {
        super(message);
    }

}
