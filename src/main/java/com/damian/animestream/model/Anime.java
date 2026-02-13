package com.damian.animestream.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// due to the annotation hibernate automatically knows all the fields below are columns. @columns simply customizes columns
@Entity // by default JPA will make a table called the classname in lowercase "anime". otherwise you can annotate it.
public class Anime { // no constructor needed, Hibernate creates object via reflection, java provides no argument constructor by default.

    @Id
    @GeneratedValue
    private UUID id; // primary key for the table with anime, Hibernate/JPA will automatically generate the UUID due to the annotation. primary key ensures no two rows have same id, not to be confused with idempotency. idempotency is api request uniqueness.

    @Column(unique = true) // enforce idempotency so the same malId cant exist twice
    private int malId; // ID from Jikan/MyAnimeList
    private String title;

    @Column(length = 2000) // column should allow up to 2000 characters
    private String description;

    private float rating;
    private String coverUrl;
    private int year;

    // By default the relationship is "lazy", loading an anime wont load its episodes unless you do getEpisodes which will fetch the list.
    // cascade is so changes propagate. a deletion of an anime also deletes all episodes with the anime_id.
    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true) // mapped by tells hibernate this is inverse side of relationship, as in anime refers to a field in the episode class. Episode owns the foreign key (FK) anime_id.
    private List<Episode> episodes; // when you getEpisodes, Hibernate will run the query SELECT * FROM episode WHERE anime_id = ?;

    // Getters and Setters
    public UUID getId() { 
        return id; 
    }

    public int getMalId() { 
        return malId; 
    }

    public void setMalId(int malId) { 
        this.malId = malId; 
    }

    public String getTitle() { 
        return title; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDescription() { 
        return description; 
    }

    public void setDescription(String description) { 
        this.description = description; 
    }

    public float getRating() { 
        return rating; 
    }
    
    public void setRating(float rating) { 
        this.rating = rating; 
    }

    public String getCoverUrl() { 
        return coverUrl; 
    }

    public void setCoverUrl(String coverUrl) { 
        this.coverUrl = coverUrl; 
    }

    public int getYear() { 
        return year; 
    }

    public void setYear(int year) { 
        this.year = year; 
    }

    public List<Episode> getEpisodes() { 
        return episodes; 
    }

    public void setEpisodes(List<Episode> episodes) { 
        this.episodes = episodes; 
    }
}
