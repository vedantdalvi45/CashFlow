package com.vedalvi.CashFlow.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Long id) {
        super("User not found with id: " + id);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
