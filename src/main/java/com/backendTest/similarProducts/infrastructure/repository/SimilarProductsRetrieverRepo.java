package com.backendTest.similarProducts.infrastructure.repository;

import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.domain.port.infrastructure.SimilarProductsRetrieverService;
import com.backendTest.similarProducts.infrastructure.config.DestinationProperties;
import com.backendTest.similarProducts.infrastructure.exception.ProductByIdCallException;
import com.backendTest.similarProducts.infrastructure.exception.SimilarProductsCallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class SimilarProductsRetrieverRepo implements SimilarProductsRetrieverService {

    public static final String COMMA = ",";
    private final WebClient webClient;
    private final DestinationProperties repoConfig;

    public SimilarProductsRetrieverRepo(WebClient.Builder webClientBuilder,DestinationProperties repoConfig)
    {
        this.repoConfig= repoConfig;
        this.webClient= webClientBuilder.baseUrl(repoConfig.getBaseUri()).build();
    }

    @Override
    public Flux<String> getSimilarProductsById(String Id) {
        return Flux.merge(webClient.get()
                .uri(repoConfig.getBaseUri(),uriBuilder -> uriBuilder
                        .path(repoConfig.getProductByIdPath())
                        .path(Id)
                        .path(repoConfig.getSimilarIdsPath())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(String.class)
                .filter(Objects::nonNull)
                .map(this::removeArrayChars)
                .map(this::separateIdStrings)
                .doOnCancel(() -> log.error("Error! Similar products call canceled."))
                .doOnError(error -> log.error("Error during Similar products call : {}", error.getMessage(), error))
                .onErrorMap(SimilarProductsCallException::new));
    }

    private Flux<String> separateIdStrings(String idsArray) {
        List<String> idList = new ArrayList<>();
        while(idsArray.contains(COMMA)){
        int commaPosition= idsArray.indexOf(COMMA);
        idList.add(idsArray.substring(0,commaPosition-1));
        if(idsArray.length()>commaPosition+1)
        idsArray=idsArray.substring(commaPosition+2);
        }
        idList.add(idsArray);
        return Mono.just(idList).flatMapMany(Flux::fromIterable);
    }

    private String removeArrayChars(String arrayString) {
        arrayString=arrayString.substring(2);
        arrayString=arrayString.substring(0,arrayString.length()-2);
                return arrayString;
    }

    @Override
    public Mono<Product> getProductById(String product_id) {
        return webClient.get()
                .uri(repoConfig.getBaseUri(),uriBuilder -> uriBuilder
                        .path(repoConfig.getProductByIdPath())
                        .path(product_id)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnCancel(() -> log.error("Error! product by Id call canceled."))
                .doOnError(error -> log.error("Error during product by Id call : {}", error.getMessage(), error))
                .onErrorMap(ProductByIdCallException::new);
    }
}
