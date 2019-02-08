package com.bootmovies.movies.domain.user;

import com.bootmovies.movies.annotations.FieldsValuesMatch;

import javax.validation.constraints.*;

@FieldsValuesMatch(message = "{passwords.matching}",
        field = "password",
        fieldMatch = "matchingPassword")
public class UserDTO {
    @NotNull
    @NotEmpty
    @Size(min=2,max = 24,message = "{username.size}")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min=2,max = 24,message = "{password.size}")
    private String password;

    private String matchingPassword;

    @NotNull
    @Email(message = "{email.valid}")
    private String email;


    public UserDTO(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password, @NotNull @NotEmpty String matchingPassword, @NotNull @Email String email) {
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public UserDTO(){ }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
