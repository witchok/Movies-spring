package com.bootmovies.movies.data.comment;


import com.bootmovies.movies.config.EmbeddedMongoConfig;
import com.bootmovies.movies.data.repos.MovieRepository;
import com.bootmovies.movies.data.services.CommentService;
import com.bootmovies.movies.data.repos.UserRepository;
import com.bootmovies.movies.domain.movie.Comment;
import com.bootmovies.movies.domain.movie.Movie;
import com.bootmovies.movies.domain.user.User;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static com.bootmovies.movies.MoviesCreator.*;
import static com.bootmovies.movies.UsersCreator.*;

import static org.assertj.core.api.Java6Assertions.assertThat;



@ActiveProfiles("integrating-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =   {EmbeddedMongoConfig.class})
public class CommentServiceTest {
    private static MongoDatabase mongoDatabase;
    private static MongoCollection userCollection;
    private static MongoCollection movieCollection;

    private static final User[] users = {
            createSimpleUser("id1","user1","user1@email.com"),
            createSimpleUser("id2","user2","user2@email.com"),
            createSimpleUser("id3","user3","user3@email.com"),
            createSimpleUser("id4","user4","user4@email.com"),
    };

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CommentService commentService;

    @BeforeClass
    public static void prepare(){
        MongoClient mongoClient = new MongoClient(new ServerAddress(EmbeddedMongoConfig.IP, EmbeddedMongoConfig.PORT));
        mongoDatabase = mongoClient.getDatabase("test");
        userCollection = mongoDatabase.getCollection("movieUsers");
        movieCollection = mongoDatabase.getCollection("movies");
    }

    @BeforeEach
    public void prepareDB(){
        for (int i = 0; i < users.length; i++) {
            userRepository.save(users[i]);
        }
    }

    @AfterEach
    public void clean(){
        userCollection.drop();
        movieCollection.drop();
    }

    @Test
    public void shouldSaveComments(){
        Comment comment1 = new Comment("user1","message", new Date());
        Comment comment2 = new Comment("user1","message", new Date());
        Comment comment3 = new Comment("user1","message", new Date());

        Movie movie1 = createSimpleMovieWithId("movie1","id1");

        movieRepository.save(movie1);

        commentService.saveComment(movie1.getId(),comment1);

        Movie movie1WithComment = movieRepository.findMovieById(movie1.getId());
        assertThat(movie1WithComment.getComments().size()).isEqualTo(1);
        assertThat(movie1WithComment.getComments().get(0).getUsername()).isEqualTo(comment1.getUsername());

        commentService.saveComment(movie1.getId(),comment2);
        commentService.saveComment(movie1.getId(),comment3);

        movie1WithComment = movieRepository.findMovieById(movie1.getId());

        assertThat(movie1WithComment.getComments().size()).isEqualTo(3);

    }

}
