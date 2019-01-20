package com.bootmovies.movies;

import static org.assertj.core.api.Assertions.assertThat;

import com.bootmovies.movies.controllers.HomeController;
import com.bootmovies.movies.controllers.MovieController;
import com.bootmovies.movies.controllers.MovieRatingController;
import com.bootmovies.movies.data.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoviesApplicationTests {
    @Autowired
    private HomeController homeController;
    @Autowired
    private MovieController movieController;
    @Autowired
    private MovieRatingController movieRatingController;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void contextLoads() {
        assertThat(homeController).isNotNull();
        assertThat(movieController).isNotNull();
        assertThat(movieRatingController).isNotNull();
        assertThat(movieRepository).isNotNull();
    }

}
