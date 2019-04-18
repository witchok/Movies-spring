package com.bootmovies.movies.domain.movie;

import java.util.Date;

public class Comment {

    private String id;
    private String message;
    private Date creationDate;

    public Comment(String id, String message, Date creationDate) {
        this.id = id;
        this.message = message;
        this.creationDate = creationDate;
    }

    public Comment(){ }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
