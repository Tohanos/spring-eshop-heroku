package com.kravchenko.springeshop.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LogAspect {

    @Pointcut("execution(* com.kravchenko.springeshop.service.*.*(..))")
    private void anyMethod() {

    }

    @AfterThrowing("anyMethod()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        log.info(joinPoint);
        System.out.println("Log AfterThrowing : " + joinPoint);
    }

    @Around("anyMethod()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Log around before : " + System.currentTimeMillis());
        log.info("Log around before : " + System.currentTimeMillis());
        Object value;
        try {
            value= pjp.proceed();
        } catch (Throwable t) {
            System.out.println("Log around after with error: " + System.currentTimeMillis());
            return null;
        }
        System.out.println("Log around after : " + System.currentTimeMillis());
        return value;
    }
}
