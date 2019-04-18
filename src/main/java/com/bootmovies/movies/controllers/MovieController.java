package com.bootmovies.movies.controllers;

import com.bootmovies.movies.config.IAuthenticationFacade;
import com.bootmovies.movies.data.services.CommentService;
import com.bootmovies.movies.data.repos.MovieRepository;
import com.bootmovies.movies.domain.movie.Comment;
import com.bootmovies.movies.domain.movie.CommentDTO;
import com.bootmovies.movies.domain.movie.Movie;
import com.bootmovies.movies.domain.user.UserDetails;
import com.bootmovies.movies.exceptions.MovieNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);
    public static final String MODEL_ERROR_MESSAGE = "errorMessage";
    public static final String MESSAGE_MOVIE_NOT_FOUND = "Sorry, but movie you requested isn't found ):";
    public static final String MODEL_FORM_COMMENT="formComment";

    @Autowired
    private MovieRepository repo;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CommentService commentService;

//    @RequestMapping (value = "/{title}", method=GET)
//    public String movieProfile(@PathVariable("title") String title,
//            Model model){
//        log.info("Get movie profile for "+title);
//        Movie movie = repo.findMovieByTitle(title);
//        log.info(movie == null ? "Movie is null" : "Movie is not null");
//        model.addAttribute("movie", movie);
//        return "moviePage";
//    }
    @RequestMapping (value = "/{id}", method=GET)
    public String movieProfile(@PathVariable("id") String id,
                               Model model){
        log.info("Get movie profile for {}", id);
        Movie movie = repo.findMovieById(id);
        if(movie == null){
            throw new MovieNotFoundException();
        }
        log.info(movie == null ? "Movie is null" : "Movie is not null");
        model.addAttribute("movie", movie);
        model.addAttribute(MODEL_FORM_COMMENT,new CommentDTO());
        return "moviePage";
    }

    @RequestMapping(value = "/sameCountry/{country}", method = GET)
    public String moviesByCountry(@PathVariable("country") String country,
        Model model) {
        log.info("Get moviesCountry");
        List<Movie> movies = repo.findMoviesByCountry(country);
        log.info("moviesCountry size: {}", movies.size());
        model.addAttribute("moviesByProperty",movies);
        model.addAttribute("messageProper","Movies filmed in "+country);
        return "moviesByProperty";
    }

    @RequestMapping(value = "/sameGenre/{genre}", method = GET)
    public String moviesByGenre(@PathVariable("genre") String genre,
                                Model model) {
        log.info("Get moviesGenre");
        List<Movie> movies = repo.findMoviesByGenre(genre);
        log.info("moviesGenre size: " + movies.size());
        model.addAttribute("moviesByProperty",movies);
        model.addAttribute("messageProper","Movies with genre: "+genre);
        return "moviesByProperty";
    }

    @RequestMapping(value = "/sameWriter/{writer}", method = GET)
    public String moviesByWriter(@PathVariable("writer") String writer,
                              Model model) {
        log.info("Get moviesWriter");
        List<Movie> movies = repo.findMoviesByWriter(writer);
        log.info("moviesWriters size: " + movies.size());
        model.addAttribute("moviesByProperty",movies);
        model.addAttribute("messageProper","Movies with writer: "+writer);
        return "moviesByProperty";
    }

    @RequestMapping(value = "/sameActor/{actor}", method = GET)
    public String moviesByActor(@PathVariable("actor") String actor,
                              Model model) {
        log.info("Get moviesActor");
        List<Movie> movies = repo.findMoviesByActor(actor);
        log.info("moviesActor size: " + movies.size());
        model.addAttribute("moviesByProperty",movies);
        model.addAttribute("messageProper","Movies with actor: "+actor);
        return "moviesByProperty";
    }

    @RequestMapping(value = "/sameDirector/{director}", method = GET)
    public String moviesByDirector(@PathVariable("director") String director,
                              Model model) {
        log.info("Get moviesDirector");
        List<Movie> movies = repo.findMoviesByDirector(director);
        log.info("moviesDirector size: " + movies.size());
        model.addAttribute("moviesByProperty",movies);
        model.addAttribute("messageProper","Movies filmed by: "+director);
        return "moviesByProperty";
    }

    @RequestMapping(value = "/searchMovie", method = GET)
    public String searchMovie(@RequestParam("reg") String reg,
                              Model model){
        if(reg!=null && reg!="") {
            log.info("Get searchMovie");
            List<Movie> movies = repo.searchForMoviesByTitleRegex(reg);
            log.info("search movies size: " + movies.size());
            model.addAttribute("moviesByProperty", movies);
            model.addAttribute("messageProper", "Movies found for request: " + reg);
        }else {
            log.info("Empty search field");
        }
        return "moviesByProperty";
    }

    @RequestMapping(value = "/{id}", method = POST)
    public String processCommentSaving(
            @PathVariable("id") String movieId,
            @ModelAttribute("formComment") @Valid CommentDTO commentDTO,
            Errors errors){
        log.info("Trying to save comment with message '{}'",commentDTO.getMessage());
        if(!errors.hasErrors()) {
            log.info("There is no form errors");

            Authentication authentication = authenticationFacade.getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String id = userDetails.getId();
            String username = userDetails.getUsername();
            log.info("Comment's author is {} with id {}",username,id);
            Comment commentToSave = convertToComment(commentDTO, id);
            commentService.saveComment(movieId, commentToSave);
            log.info("Comment saved to db");
        }
        return "redirect:/movies/"+movieId;
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(MovieNotFoundException.class)
    public String movieNotFoundHandle(Model model) {
        model.addAttribute(MODEL_ERROR_MESSAGE, MESSAGE_MOVIE_NOT_FOUND);
        return "errors/errorPage";
    }

    private static Comment convertToComment(CommentDTO commentDTO, String userId){
        return new Comment(userId, commentDTO.getMessage(), new Date());
    }
}
