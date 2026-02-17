package com.damian.animestream.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.damian.animestream.model.Anime;
import com.damian.animestream.model.Episode;
import com.damian.animestream.service.AnimeService;

import jakarta.validation.Valid;

// seperate controller, this is for CRUD operations
@RestController
@RequestMapping("/anime")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public ResponseEntity<List<Anime>> getAllAnime() {
        return ResponseEntity.ok(animeService.getAllAnime()); // spring automatically converts list of animes into json
    }

    @GetMapping("/{id}/episodes")
    public ResponseEntity<Page<Episode>> getEpisodes(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("number").ascending());  // creates a Pageable
        Page<Episode> episodesPage = animeService.getEpisodesByAnimeId(id, pageable);
        return ResponseEntity.ok(episodesPage); // episodesPage is a Page<Episode> object, this returns the content, totalElements (total episodes in DB for this anime, totalPages, size, etc)
    } // responseentity is a wrapper for HTTP response which springboot will convert into JSON
    
    // ResponseEntity gives more control than just returning a object. HTTP Stus codes beyond just 200 OK. 201 (something was created), 404 etc...
    // also allows for custom headers. metadata like location of a resource that was created etc...
    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnime(@PathVariable UUID id) {
        
        // String token = authHeader.replace("Bearer ", ""); adding JWT eventually
        // jwtService.validateToken(token);

        return ResponseEntity.ok(animeService.getAnimeById(id));
    }

    // takes in a anime json object and will map it to relative fields of the object and then convert to table row
    @PostMapping
    public ResponseEntity<Anime> addAnime(@Valid @RequestBody Anime anime) {
        Anime created = animeService.saveAnime(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable UUID id) {
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }

}