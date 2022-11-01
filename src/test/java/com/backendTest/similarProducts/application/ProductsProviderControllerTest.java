package com.backendTest.similarProducts.application;

import com.backendTest.similarProducts.application.controller.ProductsProviderController;
import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.domain.port.aplication.ProductRequestService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.backendTest.similarProducts.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductsProviderControllerTest {


    private ProductsProviderController controller;
    private final ProductRequestService service = mock(ProductRequestService.class);

    @Before
    public void setup() {
        controller = new ProductsProviderController(service);
    }


    @Test
    public void givenAInputProductWhenIsProcessedThenSimilarProductListIsReturned() {
        //GIVEN

        List<Product> similarProductsList = List.of(product1, product2);
        when(service.getSimilarProducts(INPUT_PRODUCT_ID)).thenReturn(similarProductsList);

        //WHEN
        List<Product> response = controller.getSimilarProducts(INPUT_PRODUCT_ID);

        //THEN
        assertThat(response.contains(product1)).isTrue();
        assertThat(response.contains(product2)).isTrue();
    }

}
