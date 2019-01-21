package com.bootmovies.movies.data;

import com.bootmovies.movies.MoviesApplication;
import com.bootmovies.movies.config.EmbeddedMongoConfig;
import com.bootmovies.movies.domain.Movie;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import de.flapdoodle.embed.mongo.MongodExecutable;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =   {EmbeddedMongoConfig.class})
public class MovieRepositoryTest {

    private static MongoDatabase mongoDatabase;

    private static MongoCollection movieCollection;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeClass
    public static void prepareDB(){
        MongoClient mongoClient = new MongoClient(new ServerAddress(EmbeddedMongoConfig.IP, EmbeddedMongoConfig.PORT));
        mongoDatabase = mongoClient.getDatabase("test");
        movieCollection = mongoDatabase.getCollection("movies");
    }


    @After
    public void clean() {
        movieCollection.drop();
    }


    @Test
    public void testSave() {
        Movie movieToSave = createSimpleMovie("Movie 1");
        movieRepository.save(movieToSave);
        assertThat(movieRepository.findAll().size()).isEqualTo(1);
        Movie movie = movieRepository.findAll().get(0);
        assertThat(movie.getTitle()).isEqualTo(movieToSave.getTitle());
        assertThat(movie.getId()).isNotNull();

    }


    @Test
    public void testFindMovieByTitle(){
        Movie movieToFind = createSimpleMovie("Title1");
        movieToFind.setDirector("Cameron");

        movieRepository.save(movieToFind);
        movieRepository.save(createSimpleMovie("Title2"));
        movieRepository.save(createSimpleMovie("Title3"));
        movieRepository.save(createSimpleMovie("Title4"));

        Movie resultMovie = movieRepository.findMovieByTitle(movieToFind.getTitle());

        assertThat(resultMovie).isNotNull();
        assertThat(resultMovie.getDirector()).isEqualTo(movieToFind.getDirector());
        assertThat(movieRepository.findMovieByTitle("None")).isNull();
    }

    //



    @Test
    public void testFindMovieById(){
        Movie movie1 = createSimpleMovieWithId("Title1","5bc640f898b2ea7bc57e19e2");
        Movie movie2 = createSimpleMovieWithId("Title2","5bc640f898b2ea7bc57e19e3");
        Movie movie3 = createSimpleMovieWithId("Title3","5bc640f898b2ea7bc57e19e4");
        Movie movie4 = createSimpleMovieWithId("Title4","5bc640f898b2ea7bc57e19e5");

        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);

        Movie resultMovie = movieRepository.findMovieById(movie1.getId());

        assertThat(resultMovie).isNotNull();
        assertThat(resultMovie.getTitle()).isEqualTo(movie1.getTitle());
        assertThat(movieRepository.findMovieById("5bc640f898b2ea7bc57e19e8")).isNull();
    }

    @Test
    public void testFindMoviesByActor(){
        movieRepository.save(createSimpleMovieWithActors("Title1",Arrays.asList("Actor1","Actor2")));
        Movie legend = movieRepository.save(createSimpleMovieWithActors("Legend",Arrays.asList("Actor2","Tom Hardy")));
        movieRepository.save(createSimpleMovieWithActors("Title3",Arrays.asList("Actor1","Actor6")));
        Movie taboo = movieRepository.save(createSimpleMovieWithActors("Taboo",Arrays.asList("Tom Hardy","Actor3")));
        Movie bronson = movieRepository.save(createSimpleMovieWithActors("Bronson",Arrays.asList("Tom Hardy","Actor1")));

        List<Movie> moviesWithTom = movieRepository.findMoviesByActor("Tom Hardy");
        assertThat(moviesWithTom).size().isEqualTo(3);
        assertTrue(checkIfMovieWithTitleIsInList(legend.getTitle(),moviesWithTom));
        assertTrue(checkIfMovieWithTitleIsInList(taboo.getTitle(),moviesWithTom));
        assertTrue(checkIfMovieWithTitleIsInList(bronson.getTitle(),moviesWithTom));

        assertThat(movieRepository.findMoviesByActor("Bruce Willis")).isEmpty();
    }

    @Test
    public void testFindMoviesByDirector(){
        movieRepository.save(createSimpleMovieWithDirector("Title1","director1"));
        Movie lotr = movieRepository.save(createSimpleMovieWithDirector("Lord Of The Rings","Jackson"));
        movieRepository.save(createSimpleMovieWithDirector("Avatar","Cameron"));
        Movie hobbit = movieRepository.save(createSimpleMovieWithDirector("The Hobbit","Jackson"));
        movieRepository.save(createSimpleMovieWithDirector("Alien","Cameron"));

        List<Movie> jacksonMovies = movieRepository.findMoviesByDirector("Jackson");
        assertThat(jacksonMovies).size().isEqualTo(2);
        assertTrue(checkIfMovieWithTitleIsInList(hobbit.getTitle(),jacksonMovies));
        assertTrue(checkIfMovieWithTitleIsInList(lotr.getTitle(),jacksonMovies));

        assertThat(movieRepository.findMoviesByDirector("Quentin Tarantino")).isEmpty();
    }

    @Test
    public void testFindMoviesByWriters(){
        Movie movie1 = movieRepository.save(createSimpleMovieWithWriters("Movie 1", Arrays.asList("Writer1", "Writer2", "Writer3")));
        Movie movie2 = movieRepository.save(createSimpleMovieWithWriters("Movie 2", Arrays.asList("NoName", "Writer2", "Writer5")));
        Movie movie3 = movieRepository.save(createSimpleMovieWithWriters("Movie 3", Arrays.asList("Writer1", "Writer3", "Writer35")));
        Movie movie4 = movieRepository.save(createSimpleMovieWithWriters("Movie 4", Arrays.asList("Writer3", "Writer1", "Writer6")));
        Movie movie5 = movieRepository.save(createSimpleMovieWithWriters("Movie 5", Arrays.asList("Writer", "Writer3", "Writer2")));

        List<Movie> moviesWithWriter2 = movieRepository.findMoviesByWriter("Writer2");
        assertThat(moviesWithWriter2).size().isEqualTo(3);
        assertTrue(checkIfMovieWithTitleIsInList(movie1.getTitle(),moviesWithWriter2));
        assertTrue(checkIfMovieWithTitleIsInList(movie2.getTitle(),moviesWithWriter2));
        assertTrue(checkIfMovieWithTitleIsInList(movie5.getTitle(),moviesWithWriter2));

        assertThat(movieRepository.findMoviesByWriter("Not present")).isEmpty();
    }

    @Test
    public void testFindMoviesByGenre(){
        Movie movie1 = movieRepository.save(createSimpleMovieWithGenres("Movie 1", Arrays.asList("Drama", "Criminal", "Thriller")));
        Movie movie2 = movieRepository.save(createSimpleMovieWithGenres("Movie 2", Arrays.asList("Fantasy", "Thriller")));
        Movie movie3 = movieRepository.save(createSimpleMovieWithGenres("Movie 3", Arrays.asList("Criminal", "Soap opera")));
        Movie movie4 = movieRepository.save(createSimpleMovieWithGenres("Movie 4", Arrays.asList("Docudrama", "Fantasy", "Thriller")));
        Movie movie5 = movieRepository.save(createSimpleMovieWithGenres("Movie 5", Arrays.asList("Soap opera", "Drama")));

        List<Movie> moviesByGenreThriller = movieRepository.findMoviesByGenre("Thriller");
        assertThat(moviesByGenreThriller).size().isEqualTo(3);
        assertTrue(checkIfMovieWithTitleIsInList(movie1.getTitle(),moviesByGenreThriller));
        assertTrue(checkIfMovieWithTitleIsInList(movie2.getTitle(),moviesByGenreThriller));
        assertTrue(checkIfMovieWithTitleIsInList(movie4.getTitle(),moviesByGenreThriller));

        assertThat(movieRepository.findMoviesByGenre("History")).isEmpty();
    }


    @Test
    public void testFindMoviesByCountry(){
        Movie movie1 = movieRepository.save(createSimpleMovieWithCountries("Movie 1", Arrays.asList("USA", "Ukraine", "UK")));
        Movie movie2 = movieRepository.save(createSimpleMovieWithCountries("Movie 2", Arrays.asList("USA", "Canada")));
        Movie movie3 = movieRepository.save(createSimpleMovieWithCountries("Movie 3", Arrays.asList("USA", "China", "Thailand")));
        Movie movie4 = movieRepository.save(createSimpleMovieWithCountries("Movie 4", Arrays.asList( "Switzerland", "Japan")));
        Movie movie5 = movieRepository.save(createSimpleMovieWithCountries("Movie 5", Arrays.asList("Poland", "Ukraine", "Russia")));
        List<Movie> moviesByCountryUkraine = movieRepository.findMoviesByCountry("Ukraine");
        assertThat(moviesByCountryUkraine).size().isEqualTo(2);
        assertTrue(checkIfMovieWithTitleIsInList(movie1.getTitle(),moviesByCountryUkraine));
        assertTrue(checkIfMovieWithTitleIsInList(movie5.getTitle(),moviesByCountryUkraine));

        assertThat(movieRepository.findMoviesByCountry("Brazil")).isEmpty();
    }

    @Test
    public void testSearchForMoviesByTitleRegex(){
        Movie movie1 = movieRepository.save(createSimpleMovie("West side"));
        Movie movie2 = movieRepository.save(createSimpleMovie("Wild West"));
        Movie movie3 = movieRepository.save(createSimpleMovie("Well Well"));
        Movie movie4 = movieRepository.save(createSimpleMovie("Not Matching"));
        Movie movie5 = movieRepository.save(createSimpleMovie("Once Upon A Time In The West"));

        List<Movie> moviesForRegexWes = movieRepository.searchForMoviesByTitleRegex("wes");
        assertThat(moviesForRegexWes).size().isEqualTo(3);
        assertTrue(checkIfMovieWithTitleIsInList(movie1.getTitle(),moviesForRegexWes));
        assertTrue(checkIfMovieWithTitleIsInList(movie2.getTitle(),moviesForRegexWes));
        assertTrue(checkIfMovieWithTitleIsInList(movie5.getTitle(),moviesForRegexWes));

        List<Movie> moviesForRegexWe = movieRepository.searchForMoviesByTitleRegex("we");
        assertThat(moviesForRegexWe).size().isEqualTo(4);
        assertFalse(checkIfMovieWithTitleIsInList(movie4.getTitle(),moviesForRegexWes));

    }


    //TODO: other tests

    private static Movie createSimpleMovie(String name){
        List<String> countries = new ArrayList<>();
        countries.add("USA");
        return new Movie(name, 2012, "R", 120, "Jackson",
                null,
                null,null,null,"plot","url", null, null, 23,
                null, "movie");
    }

    private static Movie createSimpleMovieWithActors(String name, List<String> actors){
        Movie movie = createSimpleMovie(name);
        movie.setActors(actors);
        return movie;
    }

    private static Movie createSimpleMovieWithWriters(String name, List<String> writers){
        Movie movie = createSimpleMovie(name);
        movie.setWriters(writers);
        return movie;
    }

    private static Movie createSimpleMovieWithDirector(String name, String director){
        Movie movie = createSimpleMovie(name);
        movie.setDirector(director);
        return movie;
    }

    private static Movie createSimpleMovieWithGenres(String name, List<String> genres){
        Movie movie = createSimpleMovie(name);
        movie.setGenres(genres);
        return movie;
    }

    private static Movie createSimpleMovieWithCountries(String name, List<String> countries){
        Movie movie = createSimpleMovie(name);
        movie.setCountries(countries);
        return movie;
    }

    private static Movie createSimpleMovieWithId(String name, String id){
        Movie movie = createSimpleMovie(name);
        movie.setId(id);
        return movie;
    }

    private static boolean checkIfMovieWithTitleIsInList( String title, List<Movie> movies){
        for (Movie movie: movies){
            if (movie.getTitle().equals(title)) return true;
        }
        return false;
    }
}
