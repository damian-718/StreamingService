package com.damian.animestream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// in order for springboot to make external requests to jikan, this config is needed with bean to annotate for external object. 
// springboot on startup intilizes all beans. webclientconfig is a webclientconfig instance.wenclientbuilder is a WebClient.Builder instance
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}