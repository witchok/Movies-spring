package com.bootmovies.movies.controllers;

import com.bootmovies.movies.repositories.MovieRepository;
import com.bootmovies.movies.domain.movie.Movie;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static com.bootmovies.movies.MoviesCreator.*;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHome() throws Exception{

        Movie movieToCheck = createSimpleMovie("Movie1");
        when(movieRepository.findMoviesByYearGreaterThan(HomeController.YEAR,
                PageRequest.of(0,4, new Sort(Sort.Direction.DESC, "year"))))
                .thenReturn(Arrays.asList(
                        movieToCheck,
                        createSimpleMovie("Movie2"),
                        createSimpleMovie("Movie3"),
                        createSimpleMovie("Movie4")

                ));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recentMovies", hasSize(4)))
                .andExpect(model().attribute("recentMovies", hasItem(movieToCheck)))
                .andExpect(view().name("home"));
    }
}
