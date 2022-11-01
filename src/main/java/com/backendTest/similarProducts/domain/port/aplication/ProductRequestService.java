package com.backendTest.similarProducts.domain.port.aplication;

import com.backendTest.similarProducts.domain.model.Product;

import java.util.List;


public interface ProductRequestService {

    List<Product> getSimilarProducts(String id);
}
