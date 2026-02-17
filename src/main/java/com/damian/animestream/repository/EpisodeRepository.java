package com.damian.animestream.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.damian.animestream.model.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, UUID> {

    // fetch a page of episodes for a given anime
    // jpa knows by method name to orderbyasc
    Page<Episode> findByAnimeIdOrderByNumberAsc(UUID animeId, Pageable pageable);
}
