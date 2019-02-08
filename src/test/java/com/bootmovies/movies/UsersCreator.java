package com.bootmovies.movies;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class UsersCreator {
    public static User createSimpleUser(String userName, String email){
        Set<String> stringRoles = new HashSet<>();
        stringRoles.add(UserRole.USER.toString());
        return new User(userName,
                "encodedPassword",
                email,
                stringRoles,
                new Date());
    }
    public static User createSimpleUser(String id, String userName, String email){
        User user = createSimpleUser(userName,email);
        user.setId(id);
        return user;
    }

    public static User createSimpleUser(String id, String userName, String password, String email){
        User user = createSimpleUser(id,userName,email);
        user.setEncodedPassword(password);
        return user;
    }

    public static UserDTO createSimpleUserDTO(String userName, String email){
        return new UserDTO(userName,
                "password","password",
                email);
    }

    public static UserDTO createSimpleUserDTO(String userName, String email, String password, String matchPassword){
        return new UserDTO(userName,
                password,matchPassword,
                email);
    }

    public static UserDTO createSimpleUserDTO(String userName, String email, String password){
        return new UserDTO(userName,
                password,password,
                email);
    }
}
