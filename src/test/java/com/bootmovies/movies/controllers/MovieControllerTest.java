package com.bootmovies.movies.controllers;

import com.bootmovies.movies.config.IAuthenticationFacade;
import com.bootmovies.movies.data.repos.UserRepository;
import com.bootmovies.movies.data.services.CommentService;
import com.bootmovies.movies.data.repos.MovieRepository;

import com.bootmovies.movies.domain.movie.Comment;
import com.bootmovies.movies.domain.movie.Movie;
import com.bootmovies.movies.domain.user.UserDetails;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static com.bootmovies.movies.MoviesCreator.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class, secure = false)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

    @MockBean
    private MovieRepository movieRepository;


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testMovieProfileById() throws Exception {
        String id = "5bc640f898b2ea7bc57e19e2";
        when(movieRepository.findMovieById (id))
                .thenReturn(createSimpleMovieWithId("testMovie",id));

        mockMvc.perform(get("/movies/5bc640f898b2ea7bc57e19e2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("movie",hasProperty("id", Matchers.equalTo(id))))
                .andExpect(view().name("moviePage"));
    }

    @Test
    public void testMovieNotFoundById() throws Exception {
        String id = "5bc640f898b2ea7bc57e19e2";
        when(movieRepository.findMovieById (id))
                .thenReturn(null);

        mockMvc.perform(get("/movies/5bc640f898b2ea7bc57e19e2"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("errors/errorPage"))
                .andExpect(model().attribute(MovieController.MODEL_ERROR_MESSAGE,
                        Matchers.equalTo(MovieController.MESSAGE_MOVIE_NOT_FOUND)));
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

//    @Test
//    public void shouldPostComment() throws Exception {
//        String movieId = "id123";
//        Comment comment = new Comment("message", new Date(), "user1");
////        when(authenticationFacade.getAuthentication().getPrincipal()).thenReturn(SecurityContextHolder.getContext().getAuthentication())
////                .thenReturn(new UserDetails("1","user1","password", null));
//        when(commentService.saveComment(movieId,comment))
//                .thenReturn(new Movie());
//
//        mockMvc.perform(post("/movies/"+movieId)
//                .param("message", comment.getMessage()))
//            .andExpect(status().isOk())
//            .andExpect(view().name("movieProfile"));
//    }

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
