package com.bootmovies.movies.controllers;

import com.bootmovies.movies.data.MovieRepository;

import org.hamcrest.Matchers;
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
                .andExpect(model().attribute("movie",hasProperty("id", Matchers.equalTo(id))))
                .andExpect(view().name("moviePage"));
    }

    @Test
    public void testMoviesByCountry() throws Exception {
        when(movieRepository.findMoviesByCountry("USA"))
                .thenReturn(Arrays.asList(
                        createSimpleMovieWithCountries("Movie1",Arrays.asList("USA","Ukraine")),
                        createSimpleMovieWithCountries("Movie2",Arrays.asList("Canada","USA")),
                        createSimpleMovieWithCountries("Movie3",Arrays.asList("USA","Russia"))
                ));
        mockMvc.perform(get("/movies/sameCountry/USA"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesByProperty","messageProper"))
                .andExpect(model().attribute("moviesByProperty", hasSize(3)))
                .andExpect(model().attribute("messageProper",Matchers.equalTo("Movies filmed in USA")))
                .andExpect(view().name("moviesByProperty"));
    }


    @Test
    public void testMoviesByGenre() throws Exception {
        when(movieRepository.findMoviesByGenre("Criminal"))
                .thenReturn(Arrays.asList(
                        createSimpleMovieWithGenres("Movie1",Arrays.asList("Drama","Criminal")),
                        createSimpleMovieWithGenres("Movie2",Arrays.asList("Criminal", "Thriller")),
                        createSimpleMovieWithGenres("Movie3",Arrays.asList("Sci-fi","Criminal"))
                ));
        mockMvc.perform(get("/movies/sameGenre/Criminal"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesByProperty","messageProper"))
                .andExpect(model().attribute("moviesByProperty", hasSize(3)))
                .andExpect(model().attribute("messageProper",Matchers.equalTo("Movies with genre: Criminal")))
                .andExpect(view().name("moviesByProperty"));
    }

    @Test
    public void testMoviesByDirector() throws Exception {
        when(movieRepository.findMoviesByDirector("Cameron"))
                .thenReturn(Arrays.asList(
                        createSimpleMovieWithDirector("Movie1","Cameron"),
                        createSimpleMovieWithDirector("Movie2","Cameron"),
                        createSimpleMovieWithDirector("Movie3","Cameron")
                ));
        mockMvc.perform(get("/movies/sameDirector/Cameron"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesByProperty","messageProper"))
                .andExpect(model().attribute("moviesByProperty", hasSize(3)))
                .andExpect(model().attribute("messageProper",
                        Matchers.equalTo("Movies filmed by: Cameron")))
                .andExpect(view().name("moviesByProperty"));
    }
    @Test
    public void testMoviesByActor() throws Exception {
        when(movieRepository.findMoviesByActor("Hardy"))
                .thenReturn(Arrays.asList(
                        createSimpleMovieWithActors("Movie1",Arrays.asList("Hardy","Smith")),
                        createSimpleMovieWithActors("Movie2",Arrays.asList("Willis", "Hardy")),
                        createSimpleMovieWithActors("Movie3",Arrays.asList("Jackson","Hardy"))
                ));
        mockMvc.perform(get("/movies/sameActor/Hardy"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesByProperty","messageProper"))
                .andExpect(model().attribute("moviesByProperty", hasSize(3)))
                .andExpect(model().attribute("messageProper",
                        Matchers.equalTo("Movies with actor: Hardy")))
                .andExpect(view().name("moviesByProperty"));
    }
    @Test
    public void testMoviesByWriter() throws Exception {
        when(movieRepository.findMoviesByWriter("Writer1"))
                .thenReturn(Arrays.asList(
                        createSimpleMovieWithGenres("Movie1",Arrays.asList("Writer","Writer1")),
                        createSimpleMovieWithGenres("Movie2",Arrays.asList("NoName", "Writer1")),
                        createSimpleMovieWithGenres("Movie3",Arrays.asList("Writer1"))
                ));
        mockMvc.perform(get("/movies/sameWriter/Writer1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesByProperty","messageProper"))
                .andExpect(model().attribute("moviesByProperty", hasSize(3)))
                .andExpect(model().attribute("messageProper",Matchers.equalTo("Movies with writer: Writer1")))
                .andExpect(view().name("moviesByProperty"));
    }

    @Test
    public void testSearchMovie() throws Exception {
        when(movieRepository.searchForMoviesByTitleRegex("mov"))
                .thenReturn(Arrays.asList(
                        createSimpleMovie("movie1"),
                        createSimpleMovie("movie2"),
                        createSimpleMovie("movie3")
                ));

        mockMvc.perform(get("/movies/searchMovie?reg=mov"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("moviesByProperty","messageProper"))
                .andExpect(model().attribute("moviesByProperty", hasSize(3)))
                .andExpect(model().attribute("messageProper",Matchers.equalTo("Movies found for request: mov")))
                .andExpect(view().name("moviesByProperty"));
    }
//    
//    @Test
//    public void testSearchMovieWithEmptySearchField() throws Exception {
//
//        mockMvc.perform(get("/movies/searchMovie?reg=mov"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("messageProper"))
//                .andExpect(model().attribute("messageProper", Matchers.equalTo("Movies not found for request: ")))
//    }
}
