package com.bootmovies.movies.controllers;

import com.bootmovies.movies.domain.user.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.Binding;
import javax.validation.Valid;

import java.net.BindException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/register", method = GET)
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new UserDTO());
        return "registrationForm";
    }

    @RequestMapping(value = "/register", method = POST)
    public String registerUser(
            @ModelAttribute("user") @Valid UserDTO userDTO,
            BindingResult bindingResult,
            Errors errors){

        return "redirect:/user/login";
    }
}
