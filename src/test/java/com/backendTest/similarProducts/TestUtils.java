package com.backendTest.similarProducts;

import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.infrastructure.config.DestinationProperties;
import com.backendTest.similarProducts.infrastructure.repository.SimilarProductsRetrieverRepo;
import com.google.gson.Gson;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

public class TestUtils {


    public static final String INPUT_PRODUCT_ID = "productId0";
    public static final String PRODUCT_ID_1 = "productId1";
    public static final String PRODUCT_ID_2 = "productId2";

    public static final Product product1 = new Product(PRODUCT_ID_1, "Potato", 2, true);
    public static final Product product2 = new Product(PRODUCT_ID_2, "Carrot", 3, true);


    public MockWebServer mockWebServer;

    protected void initServer() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    protected void closeServer() throws IOException {
        mockWebServer.shutdown();
        mockWebServer.close();
    }

    protected String getMockServerUrl() {
        return "http://" + mockWebServer.getHostName() + ":" + mockWebServer.getPort();
    }


    protected String enqueueServerResponse(String jsonProduct) {
        mockWebServer.enqueue(new MockResponse().setBody(jsonProduct).addHeader("Content-type", "application/json"));
        return jsonProduct;
    }

    protected String getIdListJsonString() {
        List<String> idsList = List.of(PRODUCT_ID_1, PRODUCT_ID_2);
        return new Gson().toJson(idsList);
    }

    @NotNull
    protected DestinationProperties getDestinationProperties() {
        DestinationProperties repoConfig = new DestinationProperties();
        repoConfig.setBaseUri(getMockServerUrl());
        repoConfig.setProductByIdPath("/product/");
        repoConfig.setSimilarIdsPath("/similarids");
        return repoConfig;
    }

    protected SimilarProductsRetrieverRepo webclientConfig() {
        DestinationProperties repoConfig = getDestinationProperties();
        return new SimilarProductsRetrieverRepo(WebClient.builder(), repoConfig);
    }
}
