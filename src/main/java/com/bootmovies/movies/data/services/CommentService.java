package com.bootmovies.movies.data.comment;

import com.bootmovies.movies.domain.movie.Comment;
import com.bootmovies.movies.domain.movie.Movie;
import com.bootmovies.movies.exceptions.MovieNotFoundException;
import com.bootmovies.movies.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Movie saveComment(String movieId, Comment commentToSave)
            throws UserNotFoundException, MovieNotFoundException;
//    Comment deleteComment(Movie movie, String id);
//    Comment findComment(String movieId, String commentId);

}
