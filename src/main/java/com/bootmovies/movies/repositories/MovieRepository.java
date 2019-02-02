package com.bootmovies.movies.repositories;


import com.bootmovies.movies.domain.Movie;
//import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Cacheable(value = "movieCache")
public interface MovieRepository extends MongoRepository<Movie, String>, PagingAndSortingRepository<Movie, String> {
    List<Movie> findMoviesByYearGreaterThan(int year, Pageable page);

//    @Cacheable(key="#id")
    Movie findMovieById(String id);

//    @Cacheable(key="#result.id")
    Movie findMovieByTitle(String title);

    @Query("{'countries':?0}")
    List<Movie> findMoviesByCountry(String country);

    @Query("{'genres':?0}")
    List<Movie> findMoviesByGenre (String genre);

    @Query("{'writers':?0}")
    List<Movie> findMoviesByWriter (String writer);

    @Query("{'actors':?0}")
    List<Movie> findMoviesByActor (String actor);

    List<Movie> findMoviesByDirector(String director);

    @Query("{'title':{$regex:?0, $options:'i'}}")
    List<Movie> searchForMoviesByTitleRegex (String regex);

    List<Movie> findMoviesByImdbIsNotNull(Pageable page);

    List<Movie> findMoviesByTomatoIsNotNull(Pageable page);

    List<Movie> findMoviesByMetacriticIsNotNull(Pageable page);

    @Cacheable(key="#result.id")
    Movie save(Movie movie);
}
