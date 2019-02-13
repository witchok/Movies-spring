package com.bootmovies.movies.controllers;

import com.bootmovies.movies.data.movie.MovieRepository;

import com.bootmovies.movies.domain.movie.Movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static com.bootmovies.movies.MoviesCreator.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieRatingController.class, secure = false)
public class MovieRatingControllerTest {
    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMoviesByImDbRating() throws Exception {
        int number = 100;
        Pageable pageable = PageRequest.of(0,number, new Sort(Sort.Direction.DESC, "imdb.rating"));
        List<Movie> moviesToReturn = createListMovieWithImdb(number);

        when(movieRepository.findMoviesByImdbIsNotNull(pageable))
                .thenReturn(moviesToReturn);

        mockMvc.perform(get("/movies/rating/imdb?number="+number))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("imdbMovies"))
                .andExpect(model().attribute("imdbMovies",hasSize(number)))
                .andExpect(model().attribute("imdbMovies", equalTo(moviesToReturn)))
                .andExpect(view().name("ratingPageIMDB"));
    }

    @Test
    public void testMoviesByMetacriticRating() throws Exception {
        int number = 100;
        Pageable pageable = PageRequest.of(0,number, new Sort(Sort.Direction.DESC, "metacritic"));
        List<Movie> moviesToReturn = createListMovieWithTomato(number);

        when(movieRepository.findMoviesByMetacriticIsNotNull(pageable))
                .thenReturn(moviesToReturn);

        mockMvc.perform(get("/movies/rating/metacritic?number="+number))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("metacriticMovies"))
                .andExpect(model().attribute("metacriticMovies",hasSize(number)))
                .andExpect(model().attribute("metacriticMovies", equalTo(moviesToReturn)))
                .andExpect(view().name("ratingPageMetacritic"));
    }


    @Test
    public void testMoviesByTomatoMeter() throws Exception {
        int number = 100;
        Pageable pageable = PageRequest.of(0,number, new Sort(Sort.Direction.DESC, "tomato.meter"));
        List<Movie> moviesToReturn = createListMovieWithTomato(number);

        when(movieRepository.findMoviesByTomatoIsNotNull(pageable))
                .thenReturn(moviesToReturn);

        mockMvc.perform(get("/movies/rating/tomato?number="+number))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tomatoMovies"))
                .andExpect(model().attribute("tomatoMovies",hasSize(number)))
                .andExpect(model().attribute("tomatoMovies", equalTo(moviesToReturn)))
                .andExpect(view().name("ratingPageTomato"));
    }

    private static List<Movie> createListMovie(int n){
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(createSimpleMovie("Movie"+i));
        }
        return list;
    }


    private static List<Movie> createListMovieWithImdb(int n){
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(createSimpleMovieWithImdbRating("Movie"+i,90));
        }
        return list;
    }


    private static List<Movie> createListMovieWithTomato(int n){
        List<Movie> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(createSimpleMovieWithTomatoMeter("Movie"+i,9));
        }
        return list;
    }

}
