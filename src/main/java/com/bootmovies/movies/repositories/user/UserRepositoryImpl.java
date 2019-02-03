package com.bootmovies.movies.repositories.user;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserRepositoryImpl implements CustomUserRepository {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(UserDTO userDTO, UserRole role) throws UserWithSuchEmailAlreadyExistsException, UserAlreadyExistsException{
        if(emailExists(userDTO.getEmail())) throw new UserWithSuchEmailAlreadyExistsException();
        else if(userExists(userDTO.getUsername())) throw new UserAlreadyExistsException();
        User user = new User(userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                role.toString(),
                new Date());
        return userRepository.save(user);
    }

    private boolean emailExists(String email){
        User user = userRepository.getUserByEmail(email);
        if(user != null) return true;
        return false;
    }

    private boolean userExists(String username){
        User user = userRepository.getUserByUsername(username);
        if(user != null) return true;
        return false;
    }
}
