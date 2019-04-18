package com.bootmovies.movies.data.services;

import com.bootmovies.movies.domain.movie.Comment;
import com.bootmovies.movies.domain.movie.Movie;
import com.bootmovies.movies.data.repos.MovieRepository;
import com.bootmovies.movies.data.repos.UserRepository;
import com.bootmovies.movies.exceptions.MovieNotFoundException;
import com.bootmovies.movies.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Movie saveComment(String movieId, Comment commentToSave)
            throws UserNotFoundException, MovieNotFoundException {
        Movie movie = movieRepository.findMovieById(movieId);
//        if (movie == null){
//            throw new MovieNotFoundException();
//        }
//        User user = userRepository.findUserByUsername(commentToSave.getUsername());
//        if (user == null){
//            throw new UserNotFoundException();
//        }
        if(movie.getComments() == null){
            movie.setComments(new ArrayList<>());
        }
        movie.getComments().add(commentToSave);
        return movieRepository.save(movie);
    }
    //    @Override
//    public Comment deleteComment(Movie movie, String id) {
//        return null;
//    }
//
//    @Override
//    public Comment findComment(String movieId, String commentId) {
//        return null;
//    }
}
