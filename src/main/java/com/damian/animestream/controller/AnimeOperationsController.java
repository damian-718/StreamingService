package com.damian.animestream.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damian.animestream.service.AnimeService;
// seperate controller, this is for admin operations
@RestController
@RequestMapping("/anime/operations") // seperate endpoint for operations
public class AnimeOperationsController {
    
    private final AnimeService animeService;

    public AnimeOperationsController(AnimeService animeService) {
        this.animeService = animeService;
    }

    // why is this POST and not GET? because it will create resources. creating database rows. GET is also idempotent by nature, same request returns same value. This can,however, change state.
    @PostMapping("/import-top") // currently defaults to top 25 (pagination on jikans side)
    public ResponseEntity<Void> importTopAnime() {
        animeService.importTopAnime();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}