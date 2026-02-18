package com.damian.animestream.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damian.animestream.model.AnimeDocument;
import com.damian.animestream.service.AnimeSearchService;

@RestController
@RequestMapping("/anime")
public class AnimeSearchController {

    private final AnimeSearchService searchService;

    // spring will inject the repository automatically
    public AnimeSearchController(AnimeSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimeDocument>> search(@RequestParam String q) {
        List<AnimeDocument> results = searchService.search(q);
        return ResponseEntity.ok(results);
    }
}
