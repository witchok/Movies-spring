package com.bootmovies.movies.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String id;
    private String message;
    private Date creationDate;
    private String userId;

    public Comment(String message, Date creationDate, String userId){
        this(null,message,creationDate,userId);
    }

}
