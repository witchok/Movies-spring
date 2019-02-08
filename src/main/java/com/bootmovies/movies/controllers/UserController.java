package com.bootmovies.movies.controllers;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import com.bootmovies.movies.repositories.user.UserRepository;
import com.bootmovies.movies.repositories.user.UserService;
import com.bootmovies.movies.repositories.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static final String USER_ALREADY_EXISTS_ERROR = "User with this username already exists";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS_ERROR = "User with such email already exists";


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model){
        log.info("Show registration form method");
        model.addAttribute("user", new UserDTO());
        return "registrationForm";
    }

    @RequestMapping(value = "/register", method = POST)
    public String registerUser(
            @ModelAttribute("user") @Valid UserDTO userDTO,
            Errors errors) {
        log.info("Register user method");
        log.info("Trying to registerNewAccount user: {}",userDTO.getUsername());
        if(!errors.hasErrors()) {
            log.info("No form errors found");
            userService.registerNewAccount(userDTO, UserRole.USER);
            log.info("Number of users in db: {}",userRepository.count());

            //TODO: why user null?
            log.info("Find user with this username: {}",userRepository.findUserByUsername(userDTO.getUsername()));
            return "redirect:/user/login";
        }
        else {
            log.info("Form errors found");
            return "registrationForm";
        }
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userAlreadyExistsExceptionHandler(Model model){
        log.info("User already exists");
        model.addAttribute("errorMessage",USER_ALREADY_EXISTS_ERROR);
        model.addAttribute("user", new UserDTO());
        return "registrationForm";
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(UserWithSuchEmailAlreadyExistsException.class)
    public String userWithEmailAlreadyExistsExceptionHandler(Model model){
        log.info("User with such email already exists");
        model.addAttribute("errorMessage",USER_WITH_EMAIL_ALREADY_EXISTS_ERROR);
        model.addAttribute("user", new UserDTO());
        return "registrationForm";
    }
}
