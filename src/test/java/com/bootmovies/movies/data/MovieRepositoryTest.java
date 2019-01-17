package com.bootmovies.movies.data;


import com.bootmovies.movies.domain.Movie;
import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb;
import com.lordofthejars.nosqlunit.mongodb.MongoDbConfiguration;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.SpringMongoDbRule;
import com.mongodb.MockMongoClient;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.MongoConfigurationSupport;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static com.lordofthejars.nosqlunit.mongodb.InMemoryMongoDb.InMemoryMongoRuleBuilder.newInMemoryMongoDbRule;
import static org.assertj.core.api.Java6Assertions.assertThat;

@Profile("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {FongoConfiguration.class})
//@UsingDataSet(locations = "/testBasicDb.json",loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
public class MovieRepositoryTest {
    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryTest.class);

    @ClassRule
    public static InMemoryMongoDb inMemoryMongoDb = newInMemoryMongoDbRule().build();

    @Rule
    public MongoDbRule mongoDbRule = getSpringMongoDBRule();

    private SpringMongoDbRule getSpringMongoDBRule(){
        MongoDbConfiguration mongoDbConfiguration = new MongoDbConfiguration();
        mongoDbConfiguration.setDatabaseName("study");
        MongoClient mongoClient = MockMongoClient.create(new Fongo("movieTest"));
        mongoDbConfiguration.setMongo(mongoClient);
        return new SpringMongoDbRule(mongoDbConfiguration);
    }
//
//
//    @Rule
//    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("demo-test");

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @UsingDataSet(locations = "/testBasicDb.json",loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testCountAllMovies(){
        LOGGER.info("testCountAllMovies");
        long total = movieRepository.count();
        assertThat(total).isEqualTo(7);
    }

    @Test
    @UsingDataSet(locations = "/testBasicDb.json",loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testFindMovieById(){
        LOGGER.info("testFindMovieById");
        Movie onceUponATime = movieRepository.findMovieById("5c2e11c57e7ba8fe7ce2fad4");
        assertThat(onceUponATime).isNotNull();
        assertThat(onceUponATime.getTitle()).isEqualTo("Once Upon a Time in the West");
    }

    @Test
    @UsingDataSet(locations = "/testBasicDb.json",loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testFindMoviesByCountry(){
        LOGGER.info("testFindMoviesByCountry");
        List<Movie> moviesUSA = movieRepository.findMoviesByCountry("USA");
        assertThat(moviesUSA.size()).isEqualTo(6);
        assertThat(containsMovieWithId(moviesUSA, "5c2e11c57e7ba8fe7ce2fad8")).isFalse();
    }

    @Test
    @UsingDataSet(locations = "/testBasicDb.json",loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testSearchForMoviesByTitleRegex(){
        LOGGER.info("testSearchForMoviesByTitleRegex");
        List<Movie> moviesSearch = movieRepository.searchForMoviesByTitleRegex("west");
        assertThat(moviesSearch.size()).isEqualTo(6);
        assertThat(containsMovieWithId(moviesSearch, "5c2e11c57e7ba8fe7ce2fada")).isFalse();
    }

    @Test
    @UsingDataSet(locations = "/testBasicDb.json",loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
    public void testSave(){
        LOGGER.info("testSave");
        Movie movie = new Movie("movie 1", 2012, "R", 120, "Jackson", null,
                null,null,null,"plot","url", null, null, 23,
                null, "movie");
        Movie movieAfterSave = movieRepository.save(movie);
        assertThat(movieAfterSave.getTitle()).isNotNull();
        assertThat(movieRepository.count()).isEqualTo(8);

    }
    private static boolean containsMovieWithId(List<Movie> movies, String id){
        for (Movie movie: movies){
            if (movie.getId().equals(id)) return true;
        }
        return false;
    }

//    @ComponentScan(basePackages = "com.bootmovies.movies")
//    @EnableMongoRepositories(basePackages = "com.bootmovies.movies.data")
//    @Configuration
//    public class FongoConfiguration extends MongoConfigurationSupport {
//
//
//        @Override
//        protected String getDatabaseName() {
//            return "demo-test";
//        }
//
//        @Override
//        protected Collection<String> getMappingBasePackages() {
//            return Arrays.asList(new String[]{"com.bootmovies.movies.domain"});
//        }
//
//        @Bean
//        public Mongo mongo(){
//            return new Fongo("mongo-test").getMongo();
//        }
//    }

}
