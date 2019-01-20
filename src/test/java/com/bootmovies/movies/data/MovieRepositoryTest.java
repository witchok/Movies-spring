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

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =   {EmbeddedMongoConfig.class})
public class MovieRepositoryTest {

    private static MongoDatabase mongoDatabase;

    private static MongoCollection movieCollection;


    @Autowired
    private MongodExecutable mongod;

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

    private static boolean checkIfMovieWithTitleIsInList( String title, List<Movie> movies){
        for (Movie movie: movies){
            if (movie.getTitle().equals(title)) return true;
        }
        return false;
    }
}
