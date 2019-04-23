package com.bootmovies.movies.aspects.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
@Slf4j
public class LogRepositoryMethods {
    @Pointcut("execution(* com.bootmovies.movies.data.repos.*.*(..))")
    public void repoMethodsPointcat(){}

    @Before(value = "repoMethodsPointcat()", argNames = "joinPoint")
    public void beforeRepositoryMethodCalled(JoinPoint joinPoint){
        log.info("Repository {} , method {} called", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
    }

}
