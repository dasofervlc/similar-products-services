package com.backendTest.similarProducts.domain;

import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.domain.port.infrastructure.SimilarProductsRetrieverService;
import com.backendTest.similarProducts.domain.service.SimilarProductsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.backendTest.similarProducts.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimilarProductsServiceImplTest {


    private SimilarProductsServiceImpl productsService;
    private final SimilarProductsRetrieverService repoService = mock(SimilarProductsRetrieverService.class);

    @Before
    public void setup() {
        productsService = new SimilarProductsServiceImpl(repoService);
    }

    @Test
    public void givenAProductIdWhenRepoIsCalledThenAListOfProductsIsReturned() {
        //GIVEN

        Flux<String> similarProductsList = Flux.just(PRODUCT_ID_1, PRODUCT_ID_2);
        when(repoService.getSimilarProductsById(INPUT_PRODUCT_ID)).thenReturn(similarProductsList);
        when(repoService.getProductById("productId1")).thenReturn(Mono.just(product1));
        when(repoService.getProductById("productId2")).thenReturn(Mono.just(product2));

        //WHEN
        List<Product> response = productsService.getSimilarProducts(INPUT_PRODUCT_ID);

        //THEN
        assertThat(response.contains(product1)).isTrue();
        assertThat(response.contains(product2)).isTrue();
    }
}
