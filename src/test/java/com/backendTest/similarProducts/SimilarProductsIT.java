package com.backendTest.similarProducts;

import com.backendTest.similarProducts.application.controller.ProductsProviderController;
import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.infrastructure.config.DestinationProperties;
import com.backendTest.similarProducts.infrastructure.repository.SimilarProductsRetrieverRepo;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimilarProductsIT extends TestUtils {

    @Autowired
    private ProductsProviderController controller;

    @Autowired
    SimilarProductsRetrieverRepo retrieverRepo;


    @Before
    public void setup() throws IOException {
        super.initServer();
        DestinationProperties repoConfig = getDestinationProperties();
        ReflectionTestUtils.setField(retrieverRepo, "repoConfig", repoConfig);
        ReflectionTestUtils.setField(retrieverRepo, "webClient", WebClient.builder().baseUrl(repoConfig.getBaseUri()).build());

    }

    @After
    public void shutdown() throws IOException {
        super.closeServer();
    }

    @Test
    public void givenProductIdThenRequestAListOfSimilarProductsThenListIsReturned() {

        //GIVEN
        super.enqueueServerResponse(getIdListJsonString());
        super.enqueueServerResponse(new Gson().toJson(product1));
        super.enqueueServerResponse(new Gson().toJson(product2));

        //WHEN
        List<Product> response = controller.getSimilarProducts(INPUT_PRODUCT_ID);

        //THEN
        assertThat(response.contains(product1)).isTrue();
        assertThat(response.contains(product2)).isTrue();

    }
}
