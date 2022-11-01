package com.backendTest.similarProducts.application.controller;

import com.backendTest.similarProducts.domain.model.Product;
import com.backendTest.similarProducts.domain.port.aplication.ProductRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ProductsProviderController {

    private final ProductRequestService service;

    @GetMapping(value = {"/product/{productId}/similar"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getSimilarProducts(@RequestParam(name = "productId") String id) {

        return service.getSimilarProducts(id);

    }
}
