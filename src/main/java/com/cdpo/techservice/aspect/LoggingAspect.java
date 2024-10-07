package com.cdpo.techservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

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
        LOGGER.info(LOG_BEFORE, joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    // After advice to log method exit
    @After(CONTROLLER_METHOD_CALL)
    public void logAfter(JoinPoint joinPoint) {
        LOGGER.info(LOG_AFTER, joinPoint.getSignature().getName());
    }

    // After throwing advice to log exceptions
    @AfterThrowing(pointcut = CONTROLLER_METHOD_CALL, throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        LOGGER.error(LOG_EX, joinPoint.getSignature().getName(), ex.getMessage());
    }
}