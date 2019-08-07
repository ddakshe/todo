package com.kennen.config;

import com.kennen.service.TodoCacheService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AspectConfig {

    @Autowired
    private TodoCacheService todoCacheService;

    @Around("execution(* com.kennen.service.TodoService.*Todo(..)))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        todoCacheService.refresh(getUsername());
        log.info("refresh cache - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
        return result;
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
