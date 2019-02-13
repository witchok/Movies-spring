package com.bootmovies.movies.domain.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import java.util.Date;

public class Comment {

    private String username;
    private String message;
    private Date creationDate;

    public Comment(String username, String message, Date creationDate) {
        this.username = username;
        this.message = message;
        this.creationDate = creationDate;
    }

    public Comment(){ }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
