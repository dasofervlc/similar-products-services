package com.backendTest.similarProducts.infrastructure;

import com.backendTest.similarProducts.TestUtils;
import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.infrastructure.exception.ProductByIdCallException;
import com.backendTest.similarProducts.infrastructure.exception.SimilarProductsCallException;
import com.backendTest.similarProducts.infrastructure.repository.SimilarProductsRetrieverRepo;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

public class SimilarProductsRetrieverRepoTest extends TestUtils {

    private SimilarProductsRetrieverRepo retrieverRepo;

    @Before
    public void init() throws IOException {
        super.initServer();
    }

    @After
    public void shutdown() throws IOException {
        super.closeServer();
    }

    @Test
    public void givenProductIdWhenCallGetSimilarProductsByIdThenRetrieveAListOfProductIds() {
        try {
            retrieverRepo = webclientConfig();

            enqueueServerResponse(getIdListJsonString());

            Flux<String> productList = retrieverRepo.getSimilarProductsById(INPUT_PRODUCT_ID);

            StepVerifier.create(productList)
                    .expectNextMatches(row -> row.startsWith(PRODUCT_ID_1))
                    .expectNextMatches(row -> row.startsWith(PRODUCT_ID_2))
                    .verifyComplete();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void givenProductIdWhenCallGetProductByIdThenRetrieveAProduct() {
        try {
            retrieverRepo = webclientConfig();

            enqueueServerResponse(new Gson().toJson(product1));

            Mono<Product> outputProduct = retrieverRepo.getProductById(PRODUCT_ID_1);
            StepVerifier.create(outputProduct)
                    .expectNext(product1)
                    .verifyComplete();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test
    public void givenInvalidProductIdWhenCallGetSimilarProductsByIdThenExceptionIsRaised() {
        try {
            retrieverRepo = webclientConfig();

            String jsonList = enqueueServerResponse("");

            Flux<String> productList = retrieverRepo.getSimilarProductsById(INPUT_PRODUCT_ID);
            StepVerifier.create(productList)
                    .expectError(SimilarProductsCallException.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void givenInvalidProductIdWhenCallGetProductByIdThenExceptionIsRaised() {
        try {
            retrieverRepo = webclientConfig();

            enqueueServerResponse("");

            Mono<Product> outputProduct = retrieverRepo.getProductById(PRODUCT_ID_1);
            StepVerifier.create(outputProduct)
                    .expectError(ProductByIdCallException.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
