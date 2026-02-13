package com.damian.animestream.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.damian.animestream.model.Anime;
import com.damian.animestream.repository.AnimeRepository;

// Spring-manages service bean (singleton). Instantiate and inject dependencies
@Service
public class AnimeService {

    private final AnimeRepository animeRepository; // defining animeRepository as a JPA repository is what gives the CRUD operations to talk to database without writing SQL

    public AnimeService(AnimeRepository animeRepository) { // inject an instance of animerepository here via spring
        this.animeRepository = animeRepository;
    }

    // database calls
    // wont return raw rows but fully constructed anime objects for each row due to the @entity annotation
    // the models allow for a way for jpa to be able to map java objects to tables and rows and vice versa...
    public List<Anime> getAllAnime() {
        return animeRepository.findAll();
    }

    public Anime getAnimeById(UUID id) { // fetch via parimary key
        return animeRepository.findById(id).orElse(null);
    }

    public Anime saveAnime(Anime anime) {
        return animeRepository.save(anime);
    }

    public void deleteAnime(UUID id) {
        animeRepository.deleteById(id);
    }
}
