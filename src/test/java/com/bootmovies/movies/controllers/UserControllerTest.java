package com.bootmovies.movies.controllers;

//import com.bootmovies.movies.config.TestWebSecurityConfig;
import com.bootmovies.movies.config.WebSecurityConfig;
import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import com.bootmovies.movies.repositories.MovieRepository;
import com.bootmovies.movies.repositories.user.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static com.bootmovies.movies.UsersCreator.*;
import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.Assert.*;

//@ActiveProfiles("integrating-test")
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = TestWebSecurityConfig.class)
@WebMvcTest(value = UserController.class, secure = false)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
//    @Autowired
//    private WebApplicationContext context;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void setup(){
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }
    @Test
    public void shouldReturnRegisterPage() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationForm"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void shouldProcessRegistration() throws Exception {
        UserDTO userDTO = createSimpleUserDTO("user1","str@email.com", "password");
        User userAfterSave = createSimpleUser("5dcd",userDTO.getUsername() ,userDTO.getPassword(),userDTO.getEmail());

        mockMvc.perform(post("/user/register")
                    .param("username",userDTO.getUsername())
                    .param("email",userDTO.getEmail())
                    .param("password", userDTO.getPassword())
                    .param("matchingPassword", userDTO.getMatchingPassword()))
                .andExpect(redirectedUrl("/user/login"));
        ArgumentCaptor<UserDTO> formObjectArgument = ArgumentCaptor.forClass(UserDTO.class);
        ArgumentCaptor<UserRole> roleArgument = ArgumentCaptor.forClass(UserRole.class);

        verify(userService, times(1))
                .registerNewAccount(formObjectArgument.capture(), roleArgument.capture());
        verifyNoMoreInteractions(userService);

        UserDTO formObject = formObjectArgument.getValue();
        assertEquals(formObject.getUsername(),userDTO.getUsername());
        assertEquals(formObject.getPassword(),userDTO.getPassword());
        assertEquals(formObject.getMatchingPassword(),userDTO.getMatchingPassword());
        assertEquals(formObject.getEmail(),userDTO.getEmail());
    }

    @Test
    public void shouldHandleUserWithSuchEmailAlreadyExistsException() throws Exception {
        UserDTO userDTO = createSimpleUserDTO("user1","str@email.com", "password");
        User userAfterSave = createSimpleUser("5dcd",userDTO.getUsername() ,userDTO.getPassword(),userDTO.getEmail());
        when(userService.registerNewAccount(userDTO, UserRole.USER))
                .thenThrow(new UserWithSuchEmailAlreadyExistsException());

        mockMvc.perform(post("/user/register")
                .flashAttr("user",userDTO))
            .andExpect(status().isConflict())
            .andExpect(view().name("registrationForm"))
            .andExpect(model().attribute("errorMessage",UserController.USER_WITH_EMAIL_ALREADY_EXISTS_ERROR));

    }

    @Test
    public void shouldHandleUserAlreadyExistsException() throws Exception {
        UserDTO userDTO = createSimpleUserDTO("user1","str@email.com", "password");
        User userAfterSave = createSimpleUser("5dcd",userDTO.getUsername() ,userDTO.getPassword(),userDTO.getEmail());
        when(userService.registerNewAccount(userDTO, UserRole.USER))
                .thenThrow(new UserAlreadyExistsException());

        mockMvc.perform(post("/user/register")
                .flashAttr("user",userDTO))
                .andExpect(status().isConflict())
                .andExpect(view().name("registrationForm"))
                .andExpect(model().attribute("errorMessage",UserController.USER_ALREADY_EXISTS_ERROR));

    }


    @Test
    public void shouldHaveFormError() throws Exception {
        UserDTO userDTO = createSimpleUserDTO("u", "str", "p");

        mockMvc.perform(post("/user/register")
                .param("username", userDTO.getUsername())
                .param("email", userDTO.getEmail())
                .param("password", userDTO.getPassword())
                .param("matchingPassword", userDTO.getMatchingPassword()))
                .andExpect(model().errorCount(3))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNotMatchingPassword() throws Exception {
        UserDTO userDTO = createSimpleUserDTO("user1","str@email.com", "password",
                "notmatch");

        mockMvc.perform(post("/user/register")
                .param("username",userDTO.getUsername())
                .param("email",userDTO.getEmail())
                .param("password", userDTO.getPassword())
                .param("matchingPassword", userDTO.getMatchingPassword()))
            .andExpect(model().errorCount(1))
            .andExpect(status().isOk());
    }

}
