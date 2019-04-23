//package com.bootmovies.movies.config;
//
//import com.bootmovies.movies.domain.enums.UserRole;
//import org.springframework.context.annotation.*;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Profile("controllerMethodsPointcat")
//@Primary
//@Configuration
//@EnableWebSecurity
////@ComponentScan(basePackages = "com.bootmovies.movies",
////        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfig.class)})
////@EnableMongoRepositories("com.bootmovies.movies.data")
//public class TestWebSecurityConfig extends MovieAbstractWebSecurityConfig {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
////                .withUser("user1")
////                .password(passwordEncoder().encode("password1"))
////                .roles(UserRole.USER.toString())
////                .and()
//                .withUser("user2")
//                .password(passwordEncoder().encode("password2"))
//                .roles(UserRole.USER.toString())
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("password3"))
//                .roles(UserRole.USER.toString(),
//                        UserRole.ADMIN.toString());
//    }
//
//}
