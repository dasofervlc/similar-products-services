package com.backendTest.similarProducts.domain.port.infrastructure;

import com.backendTest.similarProducts.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SimilarProductsRetrieverService {

    Flux<String> getSimilarProductsById(String product_id);

    Mono<Product> getProductById( String product_id);

}
