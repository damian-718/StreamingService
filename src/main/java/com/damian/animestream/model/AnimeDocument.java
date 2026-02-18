package com.damian.animestream.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "anime") // annotated for elasticsearch to know this is a document which will be indexed. all anime documents stored in a anime index to be searched.
public class AnimeDocument {

    @Id
    private int malId;
    private String title;
    private String description;
    private float rating;
    private int year;
    private String coverUrl;

    // Nested episodes inside anime
    @Field(type = FieldType.Nested) // tells Elasticsearch to treat this as nested objects
    private List<EpisodeDocument> episodes;

    // default no-args constructor (required by Elasticsearch)
    public AnimeDocument() {}

    // constructor with all fields — optional and for me to use if I want
    public AnimeDocument(int malId, String title, String description, float rating, int year, String coverUrl, List<EpisodeDocument> episodes) {
        this.malId = malId;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.year = year;
        this.coverUrl = coverUrl;
        this.episodes = episodes;
    }

    public Integer getMalId() { 
        return malId; 
    
    }
    public void setMalId(Integer malId) { 
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

    public int getYear() { 
        return year; 
    }

    public void setYear(int year) { 
        this.year = year; 
    }

    public String getCoverUrl() { 
        return coverUrl; 
    }

    public void setCoverUrl(String coverUrl) { 
        this.coverUrl = coverUrl; 
    }
}
