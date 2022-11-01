package com.backendTest.similarProducts.infrastructure.exception;

public class SimilarProductsCallException extends RuntimeException {


    public SimilarProductsCallException(Throwable e) {
        super(e.getMessage(),e);
    }
}
