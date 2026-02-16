package com.damian.animestream.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Episode {

    @Id
    @GeneratedValue
    private UUID id;  // auto-generated, no setter

    private int number;  // episode number

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 500)
    private String videoUrl;  // S3 or embedded link

    // tells JPA this is child side of relationship, many episodes to one anime object, the column name is anime_id in the table that stores Animes id.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    public UUID getId() { 
        return id; 
    }

    public int getNumber() { 
        return number; 
    }

    public void setNumber(int number) { 
        this.number = number; 
    }

    public String getTitle() { 
        return title; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getVideoUrl() { 
        return videoUrl; 
    }

    public void setVideoUrl(String videoUrl) { 
        this.videoUrl = videoUrl; 
    }

    public Anime getAnime() { 
        return anime; 
    }

    public void setAnime(Anime anime) { 
        this.anime = anime; 
    }
}
