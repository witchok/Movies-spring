package com.bootmovies.movies.controllers;

//import com.bootmovies.movies.config.TestWebSecurityConfig;
import com.bootmovies.movies.domain.enums.UserRole;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDTO;
import com.bootmovies.movies.exceptions.UserAlreadyExistsException;
import com.bootmovies.movies.exceptions.UserWithSuchEmailAlreadyExistsException;
import com.bootmovies.movies.data.user.UserRepository;
import com.bootmovies.movies.data.user.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
    private UserRepository userRepository;

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
                .andExpect(model().attributeExists(UserController.MODEL_USER_FOR_REGISTRATION));
    }

    @Test
    public void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void shouldProcessRegistration() throws Exception {
        UserDTO userDTO = createSimpleUserDTO("user1","str@email.com", "password");
        User userAfterSave = createSimpleUser("5dcd",userDTO.getUsername() ,userDTO.getPassword(),userDTO.getEmail());
        when(userService.registerNewAccount(userDTO,UserRole.USER))
                .thenReturn(userAfterSave);

        mockMvc.perform(post("/user/register")
                    .param("username",userDTO.getUsername())
                    .param("email",userDTO.getEmail())
                    .param("password", userDTO.getPassword())
                    .param("matchingPassword", userDTO.getMatchingPassword()))
                .andExpect(redirectedUrl("/user/profile?username="+userAfterSave.getUsername()));
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

    @Test
    public void shouldReturnUserProfile() throws Exception {
        String searchUsername = "user1";
        User returnedUser = createSimpleUser(searchUsername,"email@email.com");
        when(userRepository.findUserByUsername(searchUsername))
                .thenReturn(returnedUser);

        mockMvc.perform(get("/user/profile?username="+searchUsername))
                .andExpect(status().isOk())
                .andExpect(model().attribute(UserController.MODEL_USER_FOR_PROFILE,
                        hasProperty("username",Matchers.equalTo(searchUsername))))
                .andExpect(model().attribute(UserController.MODEL_USER_FOR_PROFILE,
                        hasProperty("email",Matchers.equalTo(returnedUser.getEmail()))))
                .andExpect(view().name("userProfile"));
    }

    @Test
    public void shouldHandleNotFoundedUser() throws Exception {
        String username = "user1";
        when(userRepository.findUserByUsername(username))
                .thenReturn(null);
        mockMvc.perform(get("/user/profile?username="+username))
                .andExpect(status().isNotFound())
                .andExpect(view().name("errors/errorPage"))
                .andExpect(model().attribute(UserController.MODEL_ERROR_MESSAGE,
                        Matchers.equalTo(UserController.MESSAGE_USER_NOT_FOUND)));
    }
}
