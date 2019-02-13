package com.bootmovies.movies.data.user;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User registerNewAccount(User user)
            throws UserAlreadyExistsException, UserWithSuchEmailAlreadyExistsException;
    User registerNewAccount(UserDTO userDTO, UserRole... roles)
            throws UserAlreadyExistsException, UserWithSuchEmailAlreadyExistsException;
}
