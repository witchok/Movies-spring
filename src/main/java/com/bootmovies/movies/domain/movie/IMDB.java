package com.bootmovies.movies.domain.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class IMDB {
    @Id
    private String id;
    private Double rating;
    private Integer votes;

//    public IMDB(String id, Double rating, Integer votes) {
//        this.id = id;
//        this.rating = rating;
//        this.votes = votes;
//    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
