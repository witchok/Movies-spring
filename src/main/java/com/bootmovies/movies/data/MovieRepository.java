package com.bootmovies.movies.data;


import com.bootmovies.movies.domain.Movie;
//import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findMovieByYearGreaterThan(int than);
//    @Query("{'_id':?0}")
    Movie findMovieById(String id);

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

    @Query("{'imdb':{$ne:null}}")
    List<Movie> findMoviesWithIMDBRating(Pageable pageable);

    @Query("{'tomato':{$ne:null}}")
    List<Movie> findMoviesWithTomatoRating(Pageable pageable);

    @Query("{'metacritic':{$ne:null}}")
    List<Movie> findMoviesWithMetacriticRating(Pageable pageable);


    Movie save(Movie movie);

}
