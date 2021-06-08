package com.kravchenko.springeshop.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Log4j2
public class LogAspect {

    @Pointcut("execution(* com.kravchenko.springeshop.service.*.*(..))")
    private void anyMethod() { }

    @Before("anyMethod()")
    public void logBefore(JoinPoint joinPoint) {
        String args = Arrays.stream(joinPoint.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("before " + joinPoint.toString() + ", args=[" + args + "]");
    }

    @After("anyMethod()")
    public void logAfter(JoinPoint joinPoint) {
        log.info("after " + joinPoint.toString());
    }

    @AfterThrowing("anyMethod()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        log.info(joinPoint);
        System.out.println("Log AfterThrowing : " + joinPoint);
    }

}
