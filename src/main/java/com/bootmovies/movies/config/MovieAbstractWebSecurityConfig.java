package com.bootmovies.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

public abstract class MovieAbstractWebSecurityConfig  {
    public static final int REMEMBER_ME_TIME = 30*24*60*60;
    public static final String REMEMBER_KEY = "moviesAppKey";
//    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login")
                .and()
                .rememberMe()
                    .tokenRepository(new InMemoryTokenRepositoryImpl())
                    .tokenValiditySeconds(REMEMBER_ME_TIME)
                    .key(REMEMBER_KEY)
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                    .anyRequest().permitAll();
//                .and()
//                .requiresChannel()
//                    .antMatchers("login.html").requiresSecure();
    }

//    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("/resources/**");
    }

//    @Override
    protected abstract void configure(AuthenticationManagerBuilder auth) throws Exception;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
