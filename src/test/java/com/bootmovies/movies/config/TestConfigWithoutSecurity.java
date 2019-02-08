package com.bootmovies.movies.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class TestConfigWithoutSecurity {
}
