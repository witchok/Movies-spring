package com.bootmovies.movies.controllers;

import com.bootmovies.movies.repositories.MovieRepository;
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

    @RequestMapping(value="/imdb", method=GET)
    public String moviesByImDbRating(Model model){
        logger.info("get movies by IMDB rating");
        Sort sort = new Sort(Sort.Direction.DESC,"imdb.rating");
        Pageable page = PageRequest.of(0,5, sort);
        List<Movie> movieList = movieRepository.findMoviesByImdbIsNotNull(page);
        logger.info("len of movies for imdb rating {}",movieList.size());
        model.addAttribute("imdbMovies",movieList);
        return "ratingPageIMDB";
    }

    @RequestMapping(value="/tomato", method=GET)
    public String moviesByTomatoRating(Model model){
        logger.info("get movies by tomato rating");
        Sort sort = new Sort(Sort.Direction.DESC,"tomato.meter");
        Pageable page = PageRequest.of(0,5, sort);
        List<Movie> movieList = movieRepository.findMoviesByTomatoIsNotNull(page);
        logger.info("len of movies for tomato rating "+movieList.size());
        model.addAttribute("tomatoMovies",movieList);
        return "ratingPageTomato";
    }

    @RequestMapping(value="/metacritic", method=GET)
    public String moviesByMetacriticRating(Model model){
        logger.info("get movies by metacritic rating");
        Sort sort = new Sort(Sort.Direction.DESC,"metacritic");
        Pageable page = PageRequest.of(0,5, sort);
        List<Movie> movieList = movieRepository.findMoviesByMetacriticIsNotNull(page);
        logger.info("len of movies for metacritic rating "+movieList.size());
        model.addAttribute("metacriticMovies",movieList);
        return "ratingPageMetacritic";
    }
}
