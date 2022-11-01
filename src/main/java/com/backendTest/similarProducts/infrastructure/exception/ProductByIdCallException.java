package com.backendTest.similarProducts.infrastructure.exception;

public class ProductByIdCallException extends RuntimeException {


    public ProductByIdCallException(Throwable e) {
        super(e.getMessage(),e);
    }
}
