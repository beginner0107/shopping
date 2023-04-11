package com.group.shopping.exception;

public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String message) {
      super(message);
    }
}
