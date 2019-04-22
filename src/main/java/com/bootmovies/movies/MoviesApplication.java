package com.bootmovies.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.bootmovies.movies",
        "com.bootmovies.movies.config", "com.bootmovies.movies.data"})
@EnableCaching
//@EnableAspectJAutoProxy
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
