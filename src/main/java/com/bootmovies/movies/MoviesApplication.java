package com.bootmovies.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@SpringBootApplication(scanBasePackages = {"com.bootmovies.movies",
        "com.bootmovies.movies.config", "com.bootmovies.movies.repositories"})
@EnableCaching
public class MoviesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoviesApplication.class, args);
    }
//
//    @Bean
//    CommandLineRunner init(MovieRepository movieRepository){
//        return args -> {
//            ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findAll();
//            movies.forEach(movie -> System.out.println(movie.getTitle()));
//            Movie testMovie = movieRepository.findMovieByTitle ( "Wild Wild West");
//            System.out.println(testMovie == null ? "Null" : "not null");
//            System.out.println(testMovie.getTitle());
//        };
//    }
}
