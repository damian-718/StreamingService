package com.damian.animestream.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service // intended to make API calls without going through MALs OAUTH
public class JikanService {

    private final WebClient webClient; // webclient is springs modern http client, allows my backend to call external APIs

    public JikanService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.jikan.moe/v4") // sets the default root URL for all requests to this service
                .build(); // creates a webclient instance to be used to call external APIs via springboot. In this example, its just specialized for JIKAN
    }
    // API call to JIKAN
    public String fetchTopAnimeRaw() {
        return webClient.get()
                .uri("/top/anime")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String fetchEpisodesRaw(int malId) {
        return webClient.get()
                .uri("/anime/{id}/episodes", malId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    
}