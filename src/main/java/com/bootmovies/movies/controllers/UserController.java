package com.bootmovies.movies.controllers;

import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.dto.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserNotFoundException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import com.bootmovies.movies.data.repos.UserRepository;
import com.bootmovies.movies.data.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    public static final String MODEL_ERROR_USER_NOT_FOUND="errorUser";

    public static final String MODEL_ERROR_MESSAGE = "errorMessage";
    public static final String MESSAGE_USER_NOT_FOUND = "Sorry, but user you requested isn't found ):";

    public static final String USER_ALREADY_EXISTS_ERROR = "User with this username already exists";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS_ERROR = "User with such email already exists";
    public static final String USER_WITH_ID_NOT_FOUND = "User with id '%s' not found";
    public static final String MODEL_USER_FOR_REGISTRATION="user";
    public static final String MODEL_USER_FOR_PROFILE="detailUser";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @RequestMapping(value = "/login", method = GET)
//    public String login(Model model){
//        model.addAttribute("authenticatedUser",)
//        return "login";
//    }

    @RequestMapping(value = "/profile", method = GET)
    public String userProfile(
            @RequestParam("id") String id,
            Model model){
        log.info("userProfile method");
        User user = userRepository.findUserById(id);
        if (user == null){
            log.info("User with id '{}' not found",id);
            throw new UserNotFoundException(String.format(USER_WITH_ID_NOT_FOUND,id));
        }
        model.addAttribute(MODEL_USER_FOR_PROFILE,user);
        log.info("User with name '{}' was found",id);
        return "userProfile";
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model){
        log.info("Show registration form method");
        model.addAttribute(MODEL_USER_FOR_REGISTRATION, new UserDTO());
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
            User user = userService.registerNewAccount(userDTO, UserRole.USER);
            log.info("Number of user in db: {}",userRepository.count());
//            log.info("New user's id: {}",user.getId());
            return "redirect:/login";
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

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundExceptionHandler(Model model){
        log.info("User not found");
        model.addAttribute(MODEL_ERROR_MESSAGE, MESSAGE_USER_NOT_FOUND);
        return "errors/errorPage";
    }

//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public String usernameNotFoundException(Model model, Exception e){
//        model.addAttribute(MODEL_ERROR_USER_NOT_FOUND,e.getMessage());
//        return "login";
//    }
}
