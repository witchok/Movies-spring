package com.bootmovies.movies.controllers;

import com.bootmovies.movies.repositories.MovieRepository;

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

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static com.bootmovies.movies.MoviesCreator.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieRatingController.class)
public class MovieRatingControllerTest {
    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMoviesByImDbRating() throws Exception {
        Pageable pageable = PageRequest.of(0,5, new Sort(Sort.Direction.DESC, "imdb.rating"));
        Movie movie1 = createSimpleMovieWithImdbRating("Movie1", 8.4);
        Movie movie2 = createSimpleMovieWithImdbRating("Movie2", 8.1);
        Movie movie3 = createSimpleMovieWithImdbRating("Movie3", 7.3);
        Movie movie4 = createSimpleMovieWithImdbRating("Movie4", 7.2);
        Movie movie5 = createSimpleMovieWithImdbRating("Movie5", 7.0);

        when(movieRepository.findMoviesByImdbIsNotNull(pageable))
                .thenReturn(Arrays.asList(movie1, movie2, movie3, movie4, movie5));

        mockMvc.perform(get("/movies/rating/imdb"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("imdbMovies"))
                .andExpect(model().attribute("imdbMovies",hasSize(5)))
                .andExpect(model().attribute("imdbMovies", contains(movie1,movie2,movie3, movie4, movie5)))
                .andExpect(view().name("ratingPageIMDB"));
    }

    @Test
    public void testMoviesByMetacriticRating() throws Exception {
        Pageable pageable = PageRequest.of(0,5, new Sort(Sort.Direction.DESC, "metacritic"));
        Movie movie1 = createSimpleMovieWithMetacriticRating("Movie1", 94);
        Movie movie2 = createSimpleMovieWithMetacriticRating("Movie2", 88);
        Movie movie3 = createSimpleMovieWithMetacriticRating("Movie3", 73);
        Movie movie4 = createSimpleMovieWithMetacriticRating("Movie4", 72);
        Movie movie5 = createSimpleMovieWithMetacriticRating("Movie5", 70);

        when(movieRepository.findMoviesByMetacriticIsNotNull(pageable))
                .thenReturn(Arrays.asList(movie1, movie2, movie3,movie4, movie5));

        mockMvc.perform(get("/movies/rating/metacritic"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("metacriticMovies"))
                .andExpect(model().attribute("metacriticMovies",hasSize(5)))
                .andExpect(model().attribute("metacriticMovies", contains(movie1,movie2,movie3, movie4, movie5)))
                .andExpect(view().name("ratingPageMetacritic"));
    }


    @Test
    public void testMoviesByTomatoMeter() throws Exception {
        Pageable pageable = PageRequest.of(0,5, new Sort(Sort.Direction.DESC, "tomato.meter"));
        Movie movie1 = createSimpleMovieWithTomatoMeter("Movie1", 84);
        Movie movie2 = createSimpleMovieWithTomatoMeter("Movie2", 82);
        Movie movie3 = createSimpleMovieWithTomatoMeter("Movie3", 80);
        Movie movie4 = createSimpleMovieWithTomatoMeter("Movie4", 77);
        Movie movie5 = createSimpleMovieWithTomatoMeter("Movie5", 73);

        when(movieRepository.findMoviesByTomatoIsNotNull(pageable))
                .thenReturn(Arrays.asList(movie1, movie2, movie3, movie4, movie5));

        mockMvc.perform(get("/movies/rating/tomato"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tomatoMovies"))
                .andExpect(model().attribute("tomatoMovies",hasSize(5)))
                .andExpect(model().attribute("tomatoMovies", contains(movie1,movie2,movie3, movie4, movie5)))
                .andExpect(view().name("ratingPageTomato"));
    }

}
