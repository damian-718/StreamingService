package com.damian.animestream.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

// does not need annotation since nested inside anime document. not a seperate index.
public class EpisodeDocument {

    @Field(type = FieldType.Integer)
    private int number;

    @Field(type = FieldType.Text)
    private String title;

    // default constructor
    public EpisodeDocument() {}

    public EpisodeDocument(int number, String title) {
        this.number = number;
        this.title = title;
    }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
