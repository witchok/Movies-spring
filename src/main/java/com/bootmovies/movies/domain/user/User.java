package com.bootmovies.movies.domain.user;

import com.bootmovies.movies.domain.enums.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Set;

@Document(collection = "movieUsers")
public class User {
    @Id
    private String id;
    private String username;
    @Field("password")
    private String encodedPassword;
    private String email;
    private Set<String> roles;
    private Date date;

    public User(){ }

    public User(String username, String encodedPassword, String email, Set<String> roles, Date date) {
        this(null,username,encodedPassword,email,roles,date);
    }

    public User(String id, String username, String encodedPassword, String email,Set<String> roles ,Date date) {
        this.id = id;
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.email = email;
        this.roles = roles;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj, this, "id","username","encodedPassword","email","role","date");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id","username","email");
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
