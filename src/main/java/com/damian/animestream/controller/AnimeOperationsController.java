package com.damian.animestream.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damian.animestream.service.AnimeService;
import com.damian.animestream.service.AnimeSyncService;
// seperate controller, this is for admin operations
@RestController
@RequestMapping("/anime/operations") // seperate endpoint for operations
public class AnimeOperationsController {
    
    private final AnimeService animeService;
    private final AnimeSyncService animeSyncService;

    public AnimeOperationsController(AnimeService animeService, AnimeSyncService animeSyncService) {
        this.animeService = animeService;
        this.animeSyncService = animeSyncService;
    }

    // why is this POST and not GET? because it will create resources. creating database rows. GET is also idempotent by nature, same request returns same value. This can,however, change state.
    @PostMapping("/import-top") // currently defaults to top 25 (pagination on jikans side)
    public ResponseEntity<Void> importTopAnime() throws InterruptedException {
        animeService.importTopAnime();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/sync-anime")
    public ResponseEntity<String> syncAnime() {
        animeSyncService.syncAllAnime(); // sync data from postgres into elasticsearch
        return ResponseEntity.ok("Anime sync started");
    }
}