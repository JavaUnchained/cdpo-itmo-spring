package com.cdpo.techservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private static final String SERVICE_CONTROLLER_POINTCUT = "within(com.cdpo.techservice.controller.ServiceController.*)";
    private static final String CONTROLLER_METHOD_CALL = "controllerMethods()";
    private static final String LOG_BEFORE = "Entering method: {} with arguments: {}";
    private static final String LOG_AFTER = "Exiting method: {}";
    private static final String LOG_EX = "Exception in method: {} with message: {}";


    @Pointcut(SERVICE_CONTROLLER_POINTCUT)
    public void controllerMethods() {}

    // Before advice to log method entry
    @Before(CONTROLLER_METHOD_CALL)
    public void logBefore(JoinPoint joinPoint) {
        log.info(LOG_BEFORE, joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    // After advice to log method exit
    @After(CONTROLLER_METHOD_CALL)
    public void logAfter(JoinPoint joinPoint) {
        log.info(LOG_AFTER, joinPoint.getSignature().getName());
    }

    // After throwing advice to log exceptions
    @AfterThrowing(pointcut = CONTROLLER_METHOD_CALL, throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error(LOG_EX, joinPoint.getSignature().getName(), ex.getMessage());
    }
}