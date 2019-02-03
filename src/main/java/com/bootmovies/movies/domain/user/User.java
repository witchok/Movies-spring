package com.bootmovies.movies.domain.user;

import com.bootmovies.movies.domain.enums.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "movieUsers")
public class User {
    @Id
    private String id;
    private String username;
    @Field("password")
    private String encodedPassword;
    private String email;
    private String role;
    private Date date;

    public User(){ }

    public User(String username, String encodedPassword, String email, String role, Date date) {
        this(null,username,encodedPassword,email,role, date);
    }

    public User(String id, String username, String encodedPassword, String email, String role, Date date) {
        this.id = id;
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.email = email;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
