package org.example.hw;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class TimerAspect {

    @Pointcut("within(@org.example.hw.Timer *)")
    public void beansAnnotatedWith() {

    }

    @Pointcut("@annotation(org.example.hw.Timer)")
    public void methodsAnnotatedWith() {

    }

    @Around("beansAnnotatedWith() || methodsAnnotatedWith()")
    public Object loggableAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Level level = extractLevel(joinPoint);

        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();

        long executionTime = Duration.between(start, end).toMillis();

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.atLevel(level).log("target = {}", joinPoint.getTarget().getClass());
        log.atLevel(level).log("method = {}", joinPoint.getSignature().getName());
        log.atLevel(level).log("args = {}", Arrays.toString(joinPoint.getArgs()));
        log.info ("{} - {} #{} seconds", className, methodName, (executionTime / 1000.0));

        try {
            Object returnValue = joinPoint.proceed();
            log.atLevel(level).log("result = {}", returnValue);
            return returnValue;
        } catch (Throwable e) {
            log.atLevel(level).log("exception = [{}, {}]", e.getClass(), e.getMessage());

            throw e;
        }

    }

    private Level extractLevel(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Timer annotation = signature.getMethod().getAnnotation(Timer.class);
        if (annotation != null) {
            return annotation.level();
        }

        return joinPoint.getTarget().getClass().getAnnotation(Timer.class).level();
    }}
