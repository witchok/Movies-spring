package com.bootmovies.movies.domain.dto;


import com.bootmovies.movies.domain.movie.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieProfileDTO {
    private String title;
    private int year;
    private String rated;
    private Integer runtime;
    private String director;
    private List<String> countries;
    private List<String> genres;
    private List<String> writers;
    private List<String> actors;
    private String plot;
    private String posterUrl;
    private IMDB imdb;
    private Tomato tomato;
    //    @Nullable
    private Integer metacritic;
    private Awards awards;
    private String type;
    private List<CommentShowDTO> comments;


    public MovieProfileDTO(Movie movie, List<CommentShowDTO> commentShowDTOS){
        this(movie.getTitle(),
                movie.getYear(),
                movie.getRated(),
                movie.getRuntime(),
                movie.getDirector(),
                movie.getCountries(),
                movie.getGenres(),
                movie.getWriters(),
                movie.getActors(),
                movie.getPlot(),
                movie.getPosterUrl(),
                movie.getImdb(),
                movie.getTomato(),
                movie.getMetacritic(),
                movie.getAwards(),
                movie.getType(),
                commentShowDTOS);
    }

}
