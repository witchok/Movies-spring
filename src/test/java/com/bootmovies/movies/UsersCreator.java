package com.bootmovies.movies;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;

import java.util.Date;

public abstract class UsersCreator {
    public static User createSimpleUser(String userName, String email){
        return new User(userName,
                "encodedPassword",
                email,
                UserRole.USER.toString(),
                new Date());
    }
    public static User createSimpleUser(String id, String userName, String email){
        return new User(id,userName,
                "encodedPassword",
                email,
                UserRole.USER.toString(),
                new Date());
    }

    public static UserDTO createSimpleUserDTO(String userName, String email){
        return new UserDTO(userName,
                "password","password",
                email);
    }
}
