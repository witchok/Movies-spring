package com.bootmovies.movies.controllers;

import com.bootmovies.movies.repositories.MovieRepository;
import com.bootmovies.movies.domain.movie.Movie;
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
@RequestMapping("/")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);
    public static final int YEAR = 2000;
    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(method = GET)
    public String homePage(Model model){
        //List<Movie> recentMovies = movieRepository.findMoviesByYearGreaterThan(2000);
        Pageable page = PageRequest.of(0,4, new Sort(Sort.Direction.DESC, "year"));
        List<Movie> recentMovies = movieRepository.findMoviesByYearGreaterThan(YEAR, page);
        logger.info("Len of newest movies: {}",recentMovies.size());
        model.addAttribute("recentMovies",recentMovies);
        return "home";
    }

}
