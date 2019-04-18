package com.bootmovies.movies.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentDTO {
    private static final int MAX_SIZE = 2<<15;

    @NotNull
    @NotEmpty
    @Size(max = MAX_SIZE, message = "{comment.size}")
    private String message;

    public CommentDTO(String message) {
        this.message = message;
    }

    public CommentDTO(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}