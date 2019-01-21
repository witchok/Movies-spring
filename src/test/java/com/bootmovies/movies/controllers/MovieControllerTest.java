package com.bootmovies.movies.controllers;

import com.bootmovies.movies.data.MovieRepository;
import com.bootmovies.movies.domain.Movie;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static com.bootmovies.movies.MoviesCreator.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {
    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMovieProfile() throws Exception {
        String id = "5bc640f898b2ea7bc57e19e2";
        when(movieRepository.findMovieById (id))
                .thenReturn(createSimpleMovieWithId("testMovie",id));

        mockMvc.perform(get("/movies/5bc640f898b2ea7bc57e19e2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("movie",hasProperty("id")))
                .andExpect(view().name("moviePage"));
    }
}
