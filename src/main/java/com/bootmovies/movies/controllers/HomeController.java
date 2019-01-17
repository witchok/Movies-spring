package com.bootmovies.movies.controllers;

import com.bootmovies.movies.data.MovieRepository;
import com.bootmovies.movies.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class HomeController {
    private final static Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(method = GET)
    public String homePage(Model model){
        //List<Movie> recentMovies = movieRepository.findMovieByYearGreaterThan(2000);
        List<Movie> recentMovies = movieRepository.findMovieByYearGreaterThan(1990);
        logger.info("Len of All movies: "+recentMovies.size());
        model.addAttribute("recentMovies",recentMovies);
        return "home";
    }

}
