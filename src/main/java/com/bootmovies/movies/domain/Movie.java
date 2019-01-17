package com.bootmovies.movies.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    private String title;
    private int year;
    private String rated;
    private int runtime;
    private String director;
    private ArrayList<String> countries;
    private ArrayList<String> genres;
    private ArrayList<String> writers;
    private ArrayList<String> actors;
    private String plot;
    @Field("poster")
    private String posterUrl;
    private IMDB imdb;
    private Tomato tomato;
    private int metacritic;
    private Awards awards;
    private String type;

    public Movie(String title, int year, String rated, int runtime, String director, ArrayList<String> countries, ArrayList<String> genres, ArrayList<String> writers, ArrayList<String> actors, String plot, String posterUrl, IMDB imdb, Tomato tomato, int metacritic, Awards awards, String type) {
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.runtime = runtime;
        this.director = director;
        this.countries = countries;
        this.genres = genres;
        this.writers = writers;
        this.actors = actors;
        this.plot = plot;
        this.posterUrl = posterUrl;
        this.imdb = imdb;
        this.tomato = tomato;
        this.metacritic = metacritic;
        this.awards = awards;
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
