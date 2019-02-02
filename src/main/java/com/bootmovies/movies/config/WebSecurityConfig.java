package com.bootmovies.movies.config;

import com.bootmovies.movies.domain.enums.UserRoleEnum;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //Remember for 30 days
    public static final int REMEMBER_ME_TIME = 30*24*60*60;
    public static final String REMEMBER_KEY = "moviesAppKey";
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
                .rememberMe()
                    .tokenValiditySeconds(REMEMBER_ME_TIME)
                    .key(REMEMBER_KEY)
            .and()
            .logout()
                .logoutSuccessUrl("/");
//            .and()
//            .requiresChannel()
//                .antMatchers("/login").requiresSecure();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                    .password(passwordEncoder().encode("password1"))
                    .roles(UserRoleEnum.USER.toString())
                .and()
                .withUser("user2")
                    .password(passwordEncoder().encode("password2"))
                    .roles(UserRoleEnum.USER.toString())
                .and()
                .withUser("admin")
                    .password(passwordEncoder().encode("password3"))
                    .roles(UserRoleEnum.ADMIN.toString());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
