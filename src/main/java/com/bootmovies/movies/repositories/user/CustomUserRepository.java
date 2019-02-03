package com.bootmovies.movies.repositories.user;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;

public interface CustomUserRepository {
    User save(UserDTO userDTO, UserRole role) throws UserAlreadyExistsException, UserWithSuchEmailAlreadyExistsException;
}
