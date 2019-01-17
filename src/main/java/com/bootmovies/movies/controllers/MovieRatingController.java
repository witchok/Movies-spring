package com.bootmovies.movies.controllers;

import com.bootmovies.movies.data.MovieRepository;
import com.bootmovies.movies.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/movies/rating")
public class MovieRatingController {

    private static final Logger logger = LoggerFactory.getLogger(MovieRatingController.class);

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(value="/IMDB", method=GET)
    public String moviesByImDbRating(Model model){
        logger.info("get movies by IMDB rating");
        Sort sort = new Sort(Sort.Direction.ASC,"IMDB.rating");
        Pageable page = PageRequest.of(0,3, sort);
        List<Movie> movieList = movieRepository.findMoviesForIMDBRating(page);
        logger.info("len of movies for imdb rating "+movieList.size());
        model.addAttribute("imdbMovies",movieList);
        return "ratingPageIMDB";
    }

}
