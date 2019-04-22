package com.bootmovies.movies.aspects.logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
@Slf4j
public class LogControllerMethods {
    @Pointcut("execution(* com.bootmovies.movies.controllers.HomeController.homePage(..))")
    public void test(){}

    @Before("test()")
    public void beforeTest(){
        log.info("HomeController.homePage() called");
    }
}
