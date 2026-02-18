package com.damian.animestream.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.damian.animestream.model.Anime;
import com.damian.animestream.model.AnimeDocument;
import com.damian.animestream.repository.AnimeRepository;
import com.damian.animestream.repository.AnimeSearchRepository;

@Service
public class AnimeSyncService {

    private final AnimeRepository animeRepository;
    private final AnimeSearchRepository animeSearchRepository;

    public AnimeSyncService(AnimeRepository animeRepository, AnimeSearchRepository animeSearchRepository) {
        this.animeRepository = animeRepository;
        this.animeSearchRepository = animeSearchRepository;
    }

    // sync all anime from Postgres to Elasticsearch
    public void syncAllAnime() {
        List<Anime> allAnime = animeRepository.findAll();

        for (Anime anime : allAnime) {
            syncAnime(anime);
        }
    }

    // Sync a single anime safely
    public void syncAnime(Anime anime) {
        // check if already exists in elasticsearch by malId
        boolean exists = animeSearchRepository.existsById((long) anime.getMalId());
        if (exists) return;

        AnimeDocument doc = new AnimeDocument(); // initialize empty document
        doc.setMalId(anime.getMalId());
        doc.setTitle(anime.getTitle());
        doc.setDescription(anime.getDescription());
        doc.setRating(anime.getRating());
        doc.setYear(anime.getYear());
        doc.setCoverUrl(anime.getCoverUrl());

        animeSearchRepository.save(doc); // tells elasticsearch to index this anime
    }
}
