package com.bootmovies.movies.domain.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentShowDTO {
    private String message;
    private Date creationDate;
    private UserDTOforComment user;

}
