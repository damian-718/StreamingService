package com.damian.animestream.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.damian.animestream.model.Anime;

public interface AnimeRepository extends JpaRepository<Anime, UUID> {
}
