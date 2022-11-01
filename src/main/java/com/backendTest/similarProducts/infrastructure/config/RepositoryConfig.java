package com.backendTest.similarProducts.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    @ConfigurationProperties(prefix = "infrastructure.external-endpoints.destination")
    public DestinationProperties repoConfig(){
        return new DestinationProperties();
    }
}
