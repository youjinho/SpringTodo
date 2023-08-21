package com.example.SpringTodo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TodoDownloadAspect {

    @Around("execution(* com.example.SpringTodo.TodoController.download(..))")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed(); // 원래의 메서드 호출
        long end = System.currentTimeMillis();

        log.debug("/todo/download took " + (end - start) + " milliseconds");
        return proceed;
    }
}
