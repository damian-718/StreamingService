package com.damian.animestream.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damian.animestream.model.Anime;
import com.damian.animestream.service.AnimeService;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public List<Anime> getAllAnime() {
        return animeService.getAllAnime();
    }

    @GetMapping("/{id}")
    public Anime getAnime(@PathVariable UUID id) {
        return animeService.getAnimeById(id);
    }

    // takes in a anime json object and will map it to relative fields of the object and then convert to table row
    @PostMapping
    public Anime addAnime(@RequestBody Anime anime) {
        return animeService.saveAnime(anime);
    }

    @DeleteMapping("/{id}")
    public void deleteAnime(@PathVariable UUID id) {
        animeService.deleteAnime(id);
    }
}