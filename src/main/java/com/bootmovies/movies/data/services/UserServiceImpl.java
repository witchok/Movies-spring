package com.bootmovies.movies.data.services;

import com.bootmovies.movies.data.repos.UserRepository;
import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.dto.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerNewAccount(User user)
            throws UserWithSuchEmailAlreadyExistsException, UserAlreadyExistsException{
        log.info("Register new account: {}",user.getUsername());
        if(emailExists(user.getEmail())){
            log.info("User with email '{}' already exists",user.getEmail());
            throw new UserWithSuchEmailAlreadyExistsException();
        }
        if(userExists(user.getUsername())) {
            log.info("User with username '{}' already exists",user.getUsername());
            throw new UserAlreadyExistsException();
        }
        log.info("Saving user to db .....");
        User savedUser = userRepository.save(user);
        log.info("User '{}' saved to db successfully",savedUser.getUsername());
        return savedUser;
    }

    @Override
    public User registerNewAccount(UserDTO userDTO, UserRole... roles)
            throws UserWithSuchEmailAlreadyExistsException, UserAlreadyExistsException{
        User user = convertToUser(userDTO,roles);
        return registerNewAccount(user);
    }

    private boolean emailExists(String email){
        User user = userRepository.findUserByEmail(email);
        return user != null;
    }

    private boolean userExists(String username){
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    private User convertToUser(UserDTO userDTO, UserRole... roles){
        return new User(userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getEmail(),
                Arrays.stream(roles)
                        .map(role -> role.toString())
                        .collect(Collectors.toSet()),
                new Date());
    }
}
