package com.backendTest.similarProducts.application.controller;

import com.backendTest.similarProducts.infrastructure.exception.ProductByIdCallException;
import com.backendTest.similarProducts.infrastructure.exception.SimilarProductsCallException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;


@ControllerAdvice
public class ProductsProviderControllerErrorHandler {

    @ExceptionHandler({SimilarProductsCallException.class})
    public ResponseEntity<Object> handleValidDataNotFoundError(SimilarProductsCallException e) {
        return new ResponseEntity<Object>("Invalid Product Id data received:" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({ProductByIdCallException.class})
    public ResponseEntity<Object> handleValidProductNotFoundError(ProductByIdCallException e) {
        return new ResponseEntity<Object>("Invalid Product data received:" + e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleUncaughtError(Exception e) {
        Optional<String> message = Optional.ofNullable(e.getMessage());
        return message.map(s -> new ResponseEntity<Object>(s, HttpStatus.NOT_ACCEPTABLE))
                .orElseGet(() -> new ResponseEntity<>("Error thrown, no information can be tracked", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
