package com.bootmovies.movies;

import com.bootmovies.movies.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public abstract class MoviesCreator {
    public static Movie createSimpleMovie(String name){
        List<String> countries = new ArrayList<>();
        countries.add("USA");
        return new Movie(name, 2012, "R", 120, "Jackson",
                null,
                null,null,null,"plot","url", null, null, 23,
                null, "movie");
    }

    public static Movie createSimpleMovieWithActors(String name, List<String> actors){
        Movie movie = createSimpleMovie(name);
        movie.setActors(actors);
        return movie;
    }

    public static Movie createSimpleMovieWithWriters(String name, List<String> writers){
        Movie movie = createSimpleMovie(name);
        movie.setWriters(writers);
        return movie;
    }

    public static Movie createSimpleMovieWithDirector(String name, String director){
        Movie movie = createSimpleMovie(name);
        movie.setDirector(director);
        return movie;
    }

    public static Movie createSimpleMovieWithGenres(String name, List<String> genres){
        Movie movie = createSimpleMovie(name);
        movie.setGenres(genres);
        return movie;
    }

    public static Movie createSimpleMovieWithCountries(String name, List<String> countries){
        Movie movie = createSimpleMovie(name);
        movie.setCountries(countries);
        return movie;
    }

    public static Movie createSimpleMovieWithId(String name, String id){
        Movie movie = createSimpleMovie(name);
        movie.setId(id);
        return movie;
    }

}
