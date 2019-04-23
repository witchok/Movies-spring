package com.bootmovies.movies.aspects.logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
@Slf4j
public class LogControllerMethods {
    @Pointcut("execution(* com.bootmovies.movies.controllers.*.*(..))")
    public void controllerMethodsPointcat(){}

    @Before(value = "controllerMethodsPointcat()", argNames = "joinPoint")
    public void beforeMethodStarted(JoinPoint joinPoint) {
        log.info("{} :: {} method called",joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "controllerMethodsPointcat()", returning = "viewName")
    public void afterMethodFinished(JoinPoint joinPoint, Object viewName){
        log.info("Return from {}:{}",joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName());
        if( viewName != null) {
            log.info("Returned view is {}", viewName.toString());
        }else {
            log.info("Returned view is null");
        }
    }
}
