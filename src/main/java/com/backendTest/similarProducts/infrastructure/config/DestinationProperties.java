package com.backendTest.similarProducts.infrastructure.config;

import lombok.Data;

@Data
public class DestinationProperties {

    private String baseUri;
    private String productByIdPath;
    private String SimilarIdsPath;
}
