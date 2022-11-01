package com.backendTest.similarProducts.domain.service;

import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.domain.port.aplication.ProductRequestService;
import com.backendTest.similarProducts.domain.port.infrastructure.SimilarProductsRetrieverService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SimilarProductsServiceImpl implements ProductRequestService {

    private final SimilarProductsRetrieverService similarProductsRetrieverService;

    @Override
    public List<Product> getSimilarProducts(String product_Id) {

        return similarProductsRetrieverService.getSimilarProductsById(product_Id)
                .flatMap(similarProductsRetrieverService::getProductById)
                .collect(Collectors.toList()).block();

    }
}
