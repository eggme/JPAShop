package com.example.demo.exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException(String msg) {
        super(msg);
    }
}
