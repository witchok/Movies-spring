package com.bootmovies.movies.domain.movie;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    private String title;

    public void setType(String type) {
        this.type = type;
    }

    private int year;
    private String rated;
    private int runtime;
    private String director;
    private List<String> countries;
    private List<String> genres;
    private List<String> writers;
    private List<String> actors;
    private String plot;
    @Field("poster")
    private String posterUrl;
    private IMDB imdb;
    private Tomato tomato;
    @Nullable
    private Integer metacritic;
    private Awards awards;
    private String type;

    public Movie(String title, int year, String rated, int runtime, String director, ArrayList<String> countries, ArrayList<String> genres, ArrayList<String> writers, ArrayList<String> actors, String plot, String posterUrl, IMDB imdb, Tomato tomato, Integer metacritic, Awards awards, String type) {
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


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public IMDB getImdb() {
        return imdb;
    }

    public void setImdb(IMDB imdb) {
        this.imdb = imdb;
    }

    public Tomato getTomato() {
        return tomato;
    }

    public void setTomato(Tomato tomato) {
        this.tomato = tomato;
    }

    public Integer getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(int metacritic) {
        this.metacritic = metacritic;
    }

    public Awards getAwards() {
        return awards;
    }

    public void setAwards(Awards awards) {
        this.awards = awards;
    }

    public String getType() {
        return type;
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
