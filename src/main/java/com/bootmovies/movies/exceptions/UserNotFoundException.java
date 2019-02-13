package com.bootmovies.movies.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,
        reason = "No user found")
public class UserNotFoundException extends RuntimeException {
    private String msg;

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(){ }
}
