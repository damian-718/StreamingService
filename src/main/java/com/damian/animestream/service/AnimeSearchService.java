package com.damian.animestream.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.damian.animestream.model.AnimeDocument;
import com.damian.animestream.repository.AnimeSearchRepository;

@Service
public class AnimeSearchService {

    private final AnimeSearchRepository searchRepo;

    // spring auto injects searchrepository
    public AnimeSearchService(AnimeSearchRepository searchRepo) {
        this.searchRepo = searchRepo;
    }

    public List<AnimeDocument> search(String query) {
        // combine top-level and nested search
        List<AnimeDocument> byTitle = searchRepo.findByTitleContainingIgnoreCase(query);
        List<AnimeDocument> byEpisode = searchRepo.findByEpisodeTitle(query); // nested episodes

        // merge results, remove duplicates
        Set<AnimeDocument> results = new LinkedHashSet<>(); // linked hashset is a set that maintains insertion order, so we get title matches first, then episode matches, but no duplicates
        results.addAll(byTitle);
        results.addAll(byEpisode); // we insert title then episodes, thats how the linkedlist remembers the order

        return new ArrayList<>(results);
    }
}
